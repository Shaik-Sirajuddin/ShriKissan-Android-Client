package com.shrikissan.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mancj.materialsearchbar.MaterialSearchBar
import com.shrikissan.user.databinding.FragmentHomeScreenBinding
import com.shrikissan.user.models.showToast


class HomeScreen : Fragment(), MaterialSearchBar.OnSearchActionListener {
    private lateinit var binding:FragmentHomeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater)
        binding.searchBar.setOnSearchActionListener(this)
        return binding.root
    }

    override fun onSearchStateChanged(enabled: Boolean) {

    }

    override fun onSearchConfirmed(text: CharSequence?) {
           requireActivity().showToast(text.toString())
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_SPEECH -> openVoiceRecognizer()
            else-> {}
        }
    }

    private fun openVoiceRecognizer() {
        binding.searchBar.text = "this"
        binding.searchBar.openSearch()
    }
}