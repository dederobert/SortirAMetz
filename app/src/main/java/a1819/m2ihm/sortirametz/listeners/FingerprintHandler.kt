package a1819.m2ihm.sortirametz.listeners

import a1819.m2ihm.sortirametz.R
import android.Manifest
import android.app.Activity
import android.content.RestrictionsManager
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.widget.Toast

@RequiresApi(Build.VERSION_CODES.M)
class FingerprintHandler(var activity:Activity): FingerprintManager.AuthenticationCallback() {
    private var cancellationSignal: CancellationSignal? = null
    private var nbTry = 4

    fun startAuth(fingerprintManager: FingerprintManager, cryptoObject: FingerprintManager.CryptoObject) {
        cancellationSignal = CancellationSignal()
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        Toast.makeText(activity, activity.getString(R.string.auth_error) + errString, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        Toast.makeText(activity, activity.getString(R.string.auth_help) + helpString, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationFailed() {
        Toast.makeText(activity, activity.getString(R.string.auth_failed), Toast.LENGTH_LONG).show()
        //On autorise 3 tentatives après l'utilisateur doit se logger avec mdp
        if (--nbTry == 0) {
            activity.setResult(RestrictionsManager.RESULT_DENIED)
            activity.finish()
        }
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
        Toast.makeText(activity, activity.getString(R.string.auth_succeeded), Toast.LENGTH_LONG).show()
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }
}