package pl.atlantischi.antiphoneharassment

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import pl.atlantischi.antiphoneharassment.ext.tag

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerPhoneStatusListener()

        val telephoneManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        telephoneManager.listen(object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String?) {
                super.onCallStateChanged(state, incomingNumber)
                Log.d(tag(), "onCallStateChanged, state=$state, incomingNumber=$incomingNumber")
                when(state) {
                    //电话挂断
                    TelephonyManager.CALL_STATE_IDLE -> {}
                    //电话响铃
                    TelephonyManager.CALL_STATE_RINGING -> {
                        onRinging()
                    }
                    //来电或者去电，无法区分
                    TelephonyManager.CALL_STATE_OFFHOOK -> {}
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE)
        return START_STICKY_COMPATIBILITY
    }

    private fun onRinging() {

    }

    private fun registerPhoneStatusListener() {
        val filter = IntentFilter()
        filter.addAction("android.intent.action.PHONE_STATE")
        filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL)
        registerReceiver(phoneStatusReceiver, filter)
    }

    val phoneStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d(tag(), "phoneStatusReceiver, action=$intent.action")
            when(intent.action) {
                Intent.ACTION_NEW_OUTGOING_CALL -> {

                }
                else -> {

                }
            }
        }
    }

}
