package com.shrikissan.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrikissan.user.adapters.AddressAdapter
import com.shrikissan.user.adapters.AddressCallBacks
import com.shrikissan.user.databinding.ActivityAddressBinding
import com.shrikissan.user.models.Address
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

    private fun setList(it: ArrayList<Address>?) {
        if (it == null) {
            binding.tohide.visibility = View.VISIBLE
            return
        }
        binding.tohide.visibility = View.GONE
        list.clear()
        list.addAll(it)
        adapter.notifyDataSetChanged()
    }

    override fun editAddress(pos: Int) {
        val address = Json.encodeToString(list[pos])
        val intent = Intent(this, EditAddress::class.java)
        intent.putExtra("address", address)
        startActivity(intent)
    }

    override fun removeAddress(pos: Int) {
        repository.removeAddress(list[pos])
    }

    override fun itemClicked(pos: Int) {

    }
}