package com.shrikissan.user.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.mancj.materialsearchbar.MaterialSearchBar
import com.shrikissan.user.NotificationsActivity
import com.shrikissan.user.R
import com.shrikissan.user.UpdateProfileActivity
import com.shrikissan.user.databinding.FragmentHomeScreenBinding
import com.shrikissan.user.models.showToast
import android.content.ActivityNotFoundException
import android.content.Context
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.util.*


class HomeScreen : Fragment(), MaterialSearchBar.OnSearchActionListener {
    private lateinit var binding: FragmentHomeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater)
        binding.searchBar.setOnSearchActionListener(this)
        binding.profile.setOnClickListener {
            val intent = Intent(requireContext(), UpdateProfileActivity::class.java)
            startActivity(intent)
        }
        binding.notification.setOnClickListener {
            val intent = Intent(requireContext(), NotificationsActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onSearchStateChanged(enabled: Boolean) {

    }

    override fun onSearchConfirmed(text: CharSequence?) {
        val bundle = bundleOf("isProduct" to true, "productName" to text.toString())
        val controller =
            Navigation.findNavController(requireActivity(), R.id.mainScreenContainer)
        try {
            controller.navigate(R.id.navigateToProductScreen, bundle)
        } catch (e: Exception) {
            controller.navigateUp()
            controller.navigate(R.id.navigateToProductScreen, bundle)
        }
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_SPEECH -> openVoiceRecognizer()
            else -> {}
        }
    }

    private fun openVoiceRecognizer() {
        promptSpeechInput()
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        try {
            resultLauncher.launch(intent)
        } catch (a: ActivityNotFoundException) {
            requireActivity().showToast("Voice assistant not installed in phone")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resultLauncher  = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val data = it.data
            try {
                if (it.resultCode == RESULT_OK && data != null) {
                    val query = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                    if (query != null) {
                        binding.searchBar.text = query
                        binding.searchBar.openSearch()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private lateinit var resultLauncher:ActivityResultLauncher<Intent>
}