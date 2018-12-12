package a1819.m2ihm.sortirametz

import a1819.m2ihm.sortirametz.helpers.FingerPrintHelper
import a1819.m2ihm.sortirametz.listeners.FingerprintHandler
import android.Manifest
import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import java.io.IOException
import java.lang.RuntimeException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

class FingerPrintActivity : AppCompatActivity() {

    var fingerprintManager: FingerprintManager? = null
    lateinit var keyguardManager: KeyguardManager
    var keyStore: KeyStore? = null
    var keyGenerator: KeyGenerator? = null
    var cipher: Cipher? = null
    var cryptoObject: FingerprintManager.CryptoObject? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finger_print)

        FingerPrintHelper.INSTANCE.initialised(this);

        this.keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        this.fingerprintManager = FingerPrintHelper.INSTANCE.fingerprintManager

        if (!keyguardManager.isKeyguardSecure()) {
            Toast.makeText(this, getString(R.string.no_lock_screen), Toast.LENGTH_LONG).show()
            return
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, getString(R.string.no_fingerprint_permission), Toast.LENGTH_LONG).show()
            return
        }
        if (!fingerprintManager?.hasEnrolledFingerprints()!!) {
            Toast.makeText(this, getString(R.string.no_fingerprint_enrolled), Toast.LENGTH_LONG).show()
            return
        }
        generatedKey()
        if (cipherInit()) {
            cryptoObject = FingerprintManager.CryptoObject(cipher!!)
            val helper: FingerprintHandler = FingerprintHandler(this)
            helper.startAuth(fingerprintManager!!, cryptoObject!!)
        }
    }

    private val KEY_NAME: String = "example_key"

    @TargetApi(Build.VERSION_CODES.M)
    fun generatedKey() {
        this.keyStore = KeyStore.getInstance("AndroidKeyStore")
        try {
            this.keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        }catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get KeyGenerator instance", e)
        }catch (e: NoSuchProviderException) {
            throw RuntimeException("Failed to get KeyGenerator instance", e)
        }
        try {
            keyStore?.load(null)
            keyGenerator?.init(KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build())
            keyGenerator?.generateKey()
        }
        catch (e: NoSuchAlgorithmException) {throw RuntimeException(e)}
        catch (e: InvalidAlgorithmParameterException) {throw RuntimeException(e)}
        catch (e: CertificateException) {throw RuntimeException(e)}
        catch (e: IOException) {throw RuntimeException(e)}
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun cipherInit(): Boolean {
        try {
            this.cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/" +
                            KeyProperties.BLOCK_MODE_CBC + "/" +
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
        }catch (e:NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        }catch (e:NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }

        return try {
            keyStore?.load(null)
            val key: SecretKey = this.keyStore?.getKey(KEY_NAME, null) as SecretKey
            cipher?.init(Cipher.ENCRYPT_MODE, key)
            true;
        }catch (e:KeyPermanentlyInvalidatedException) {
            false;
        }catch (e:KeyStoreException) {
            throw RuntimeException("Failed to init cipher", e)
        }catch (e:CertificateException) {
            throw RuntimeException("Failed to init cipher", e)
        }catch (e:UnrecoverableKeyException) {
            throw RuntimeException("Failed to init cipher", e)
        }catch (e:IOException) {
            throw RuntimeException("Failed to init cipher", e)
        }catch (e:NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init cipher", e)
        }catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init cipher", e)
        }
    }
}
