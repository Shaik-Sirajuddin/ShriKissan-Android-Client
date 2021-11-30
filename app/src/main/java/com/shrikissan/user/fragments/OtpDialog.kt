package com.shrikissan.user.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.widget.addTextChangedListener
import com.mukesh.OtpView
import com.shrikissan.user.R
import com.shrikissan.user.models.Constants
import kotlin.Exception

class OtpDialog: AppCompatDialogFragment() {
    private lateinit var  listener: OtpDialogListener
    private lateinit var time:TextView
    private lateinit var resend:TextView
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.otp_dialog, null)
        val number = view.findViewById<TextView>(R.id.number)
        val otpView = view.findViewById<OtpView>(R.id.otpVw)
        time = view.findViewById(R.id.timer)
        resend = view.findViewById(R.id.resend)
        val done = view.findViewById<LinearLayout>(R.id.done)
        done.setOnClickListener {
            if(otpView.text.toString().length>=6){
                listener.otpCompleted(otpView.text.toString())
            }
        }
        number.text = requireActivity().getString(R.string.please_wai) +" " + Constants.phoneNumber
        otpView.setOtpCompletionListener {
            done.setBackgroundResource(R.color.greenDark)
            done.isClickable = true
        }
        view.findViewById<TextView>(R.id.close).setOnClickListener {
            timer.cancel()
            listener.closeDialog()
        }
        otpView.addTextChangedListener {
            if(it.toString().length<6){
                done.setBackgroundResource(R.color.grey)
                done.isClickable = false
            }
        }
        resend.setOnClickListener {
            listener.resendOTP()
            resend.isClickable = false
            resend.text = requireActivity().getString(R.string.send_otp_again)
            timer.start()
        }
        timer.start()
        val dia =  AlertDialog.Builder(requireActivity())
            .setView(view)
            .create()
        dia.setOnDismissListener {
            timer.cancel()
        }
        dia.setOnCancelListener {
            timer.cancel()
        }
        dia.setCanceledOnTouchOutside(false)
        return dia
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as OtpDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString() +
                        "must implement ExampleDialogListener"
            )
        }
    }
    private var timer =object: CountDownTimer(6000,1000){
        override fun onTick(p0: Long) {
            try{
                val min:Int = (p0/1000).toInt()
                time.text = "$min"
                resend.isClickable  = false
            }
            catch (e:Exception){
                Log.e("timer",e.message.toString())
            }

        }
        override fun onFinish() {
            try{
                resend.text = getString(R.string.resend_otp)
                resend.isClickable  = true
                time.text = ""
            }
            catch (e:Exception){
                Log.e("timer",e.message.toString())
            }

        }
    }
    interface OtpDialogListener {
        fun otpCompleted(string: String)
        fun resendOTP()
        fun closeDialog()
    }
}