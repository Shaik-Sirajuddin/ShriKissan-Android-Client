package com.shrikissan.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrikissan.user.adapters.AddressAdapter
import com.shrikissan.user.adapters.AddressCallBacks
import com.shrikissan.user.databinding.ActivityAddressBinding
import com.shrikissan.user.models.Address
import com.shrikissan.user.models.showToast
import com.shrikissan.user.network.Repository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AddressActivity : AppCompatActivity(), AddressCallBacks {
    private lateinit var binding: ActivityAddressBinding
    private val list = ArrayList<Address>()
    private lateinit var adapter: AddressAdapter
    private lateinit var repository: Repository
    private var dialog: CustomProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = Repository(this)
        adapter = AddressAdapter(this, list, false, this)
        binding.addressList.layoutManager = LinearLayoutManager(this)
        binding.addressList.adapter = adapter
        binding.button.setOnClickListener {
            val intent = Intent(this, EditAddress::class.java)
            startActivity(intent)
        }
        dialog = CustomProgressDialog(this)
        dialog?.show()
    }

    override fun onResume() {
        super.onResume()
        repository.getAllAddress {
            dialog?.dismiss()
            setList(it)
        }
    }

    override fun onBackPressed() {
        dialog?.dismiss()
        super.onBackPressed()
    }
    private fun setList(it: ArrayList<Address>?) {
        binding.tohide.visibility = View.GONE
        if (it == null) {
            binding.tohide.text = resources.getString(R.string.network_not_available)
            binding.tohide.visibility = View.VISIBLE
            return
        }
        list.clear()
        list.addAll(it)
        adapter.notifyDataSetChanged()
        if(list.isEmpty()){
            binding.tohide.text = resources.getString(R.string.no_address)
            binding.tohide.visibility = View.VISIBLE
        }
    }

    override fun editAddress(pos: Int) {
        val address = Json.encodeToString(list[pos])
        val intent = Intent(this, EditAddress::class.java)
        intent.putExtra("address", address)
        startActivity(intent)
    }

    override fun removeAddress(pos: Int) {
        val dialog = CustomProgressDialog(this)
        dialog.show()
        repository.removeAddress(list[pos]){
            dialog.dismiss()
            if(it){
                list.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                if(list.isEmpty()){
                    binding.tohide.text = resources.getString(R.string.no_address)
                    binding.tohide.visibility = View.VISIBLE
                }
                else{
                    binding.tohide.visibility = View.GONE
                }
            }
            else{
                showToast("Unknown error occurred")
            }
        }
    }

    override fun itemClicked(pos: Int) {

    }
}