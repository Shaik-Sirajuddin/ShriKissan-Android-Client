package com.shrikissan.user

import android.provider.Telephony
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import android.content.BroadcastReceiver
import android.content.Context
import android.telephony.SmsMessage
import com.mukesh.OtpView


class OTPReceiver : BroadcastReceiver() {
    fun setOtpView(editText: OtpView?) {
        editText_otp = editText
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onReceive(context: Context?, intent: Intent?) {
        try{
            val smsMessages: Array<SmsMessage> = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (smsMessage in smsMessages) {
                val message_body: String = smsMessage.messageBody
                val getOTP = message_body.split(":").toTypedArray()[0]
                editText_otp!!.setText(getOTP)
            }
        }
        catch (e:Exception){

        }

    }

    companion object {
        private var editText_otp: OtpView? = null
    }
}