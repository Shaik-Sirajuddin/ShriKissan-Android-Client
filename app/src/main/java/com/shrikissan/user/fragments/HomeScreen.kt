package com.shrikissan.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mancj.materialsearchbar.MaterialSearchBar
import com.shrikissan.user.R
import com.shrikissan.user.adapters.CategoryAdapter
import com.shrikissan.user.databinding.FragmentHomeScreenBinding
import com.shrikissan.user.models.CategoryItem
import com.shrikissan.user.models.isConnected
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
         try{
             val bundle = bundleOf("isProduct" to true, "productName" to text.toString())
             val controller = Navigation.findNavController(requireActivity(),R.id.mainScreenContainer)
             controller.navigate(R.id.navigateToProductScreen,bundle)
         }
         catch(e:Exception){

         }
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