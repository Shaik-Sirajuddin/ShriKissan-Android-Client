package com.shrikissan.user

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import com.shrikissan.user.databinding.ActivitySignInBinding
import com.shrikissan.user.fragments.MobileNumberScreen
import com.shrikissan.user.fragments.OtpDialog
import com.shrikissan.user.models.Constants
import com.shrikissan.user.models.showToast
import androidx.navigation.Navigation

import androidx.navigation.fragment.NavHostFragment
import com.shrikissan.user.models.Profile
import com.shrikissan.user.network.OtpRepository
import com.shrikissan.user.network.Repository
import java.util.*


class SignInActivity : AppCompatActivity(), OtpDialog.OtpDialogListener {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var data: SharedPreferences
    private var otpDialog: OtpDialog? = null
    private lateinit var otpRepository: OtpRepository
    private var transId = ""
    private var progressDialog: CustomProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        data = getSharedPreferences(Constants.preference, Context.MODE_PRIVATE)
        val lang = data.getString(Constants.language, "en") ?: "en"
        setLanguage(lang)
        setContentView(binding.root)
        otpRepository = OtpRepository(this)
        val status = data
            .getBoolean(Constants.isLoggedIn, false)
        if (status) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun setLanguage(code: String) {
        val res = resources
        val locale = Locale(code)
        Locale.setDefault(locale)
        val config = res.configuration
        config.setLocale(locale)
        createConfigurationContext(config)
        res.updateConfiguration(config, res.displayMetrics)
    }

    override fun otpCompleted(string: String) {
        checkInUser(string)
    }

    override fun resendOTP() {
        sendOTP()
    }

    override fun closeDialog() {
        otpDialog?.dismiss()
    }

    private fun checkOTP(otp: String, onComplete: (done: Boolean, disable: () -> Unit) -> Unit) {
        progressDialog = CustomProgressDialog(this)
        progressDialog?.show()
        otpRepository.verifyOTP(transId, otp) {
            onComplete(it) {
                progressDialog?.dismiss()
            }
        }
    }

    fun sendOTP() {
        otpRepository.sendOTP(Constants.phoneNumber) {
            if (it != null) {
                transId = it
            } else {
                showToast("Invalid Number")
            }
        }
    }

    private fun checkInUser(string: String) {
        checkOTP(string) { it, onComplete ->
            if (it) {
                val repository = Repository(this)
                val profile = Profile(Constants.phoneNumber, Constants.phoneNumber, "")
                repository.addProfile(profile) { done ->
                    onComplete()
                    otpDialog?.dismiss()
                    if (done) {
                        check()
                    } else {
                        showToast("Failed to create profile")
                    }
                }


            } else {
                otpDialog?.dismiss()
                showToast("Invalid OTP")
            }
        }
    }

    private fun check() {
        data.edit {
            putString(Constants.userNumber, Constants.phoneNumber)
            putBoolean(Constants.isLoggedIn, true)
            apply()
        }
        Navigation.findNavController(binding.fragmentContainerView)
            .navigate(R.id.navigate_to_otpScreen)
    }

    fun showOTPDialog() {
        Log.d("enter", "ok")
        otpDialog = OtpDialog()
        otpDialog?.show(supportFragmentManager, "This")
    }
}