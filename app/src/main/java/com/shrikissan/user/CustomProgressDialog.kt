package com.shrikissan.user

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater

class CustomProgressDialog(context: Context): Dialog(context) {
    init {
        val params = window?.attributes
        if(params!=null){
            params.gravity = Gravity.CENTER_HORIZONTAL
            window?.attributes = params
            setTitle(null)
            setCancelable(false)
            setOnCancelListener(null)
            val view = LayoutInflater.from(context)
                .inflate(R.layout.progress_dialog,null,false)
            setContentView(view)
        }
    }
}