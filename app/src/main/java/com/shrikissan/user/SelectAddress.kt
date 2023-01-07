package com.shrikissan.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrikissan.user.adapters.AddressAdapter
import com.shrikissan.user.adapters.AddressCallBacks
import com.shrikissan.user.databinding.FragmentHomeScreenBinding
import com.shrikissan.user.databinding.FragmentSelectAddressBinding
import com.shrikissan.user.models.Address
import com.shrikissan.user.network.Repository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class SelectAddress : Fragment(), AddressCallBacks {
    private lateinit var binding: FragmentSelectAddressBinding
    private val list = ArrayList<Address>()
    private lateinit var adapter: AddressAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSelectAddressBinding.inflate(inflater)
        adapter = AddressAdapter(requireContext(), list, true, this)
        binding.addressList.layoutManager = LinearLayoutManager(requireContext())
        binding.addressList.adapter = adapter
        val repository = Repository(requireContext())
        val dialog = CustomProgressDialog(requireContext())
        dialog.show()
        repository.getAllAddress {
            dialog.dismiss()
            if(it!=null){
                list.clear()
                list.addAll(it)
                if(list.isEmpty()){
                    binding.tohide.visibility = View.VISIBLE
                }
                else{
                    binding.tohide.visibility = View.GONE
                }
                adapter.notifyDataSetChanged()
            }
            else{
                binding.tohide.visibility = View.VISIBLE
            }
        }
        return binding.root
    }

    override fun editAddress(pos: Int) {

    }

    override fun removeAddress(pos: Int) {

    }

    override fun itemClicked(pos: Int) {
        val address = Json.encodeToString(list[pos])
        val bundle = bundleOf("address" to address)
        Navigation.findNavController(binding.root)
            .navigate(R.id.navigate_to_order_page,bundle)
    }
}