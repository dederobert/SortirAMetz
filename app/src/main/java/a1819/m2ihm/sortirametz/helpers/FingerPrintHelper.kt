package a1819.m2ihm.sortirametz.helpers

import android.content.Context
import android.content.Context.FINGERPRINT_SERVICE
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.support.annotation.RequiresApi
import java.lang.IllegalStateException

@RequiresApi(Build.VERSION_CODES.M)
enum class FingerPrintHelper {
    INSTANCE;

    var initialised: Boolean = false
    var fingerprintManager: FingerprintManager? = null

    fun hasFingerPrint(): Boolean {
        if (!initialised) throw IllegalStateException("The fingerPrintHelper is not initialised")
        if (fingerprintManager!=null)
            return fingerprintManager!!.isHardwareDetected()
        else
            return false
    }

    fun initialised(context: Context) {
        if (!initialised) {
            fingerprintManager = context.getSystemService(FINGERPRINT_SERVICE) as FingerprintManager?
            initialised = true
        }
    }

}