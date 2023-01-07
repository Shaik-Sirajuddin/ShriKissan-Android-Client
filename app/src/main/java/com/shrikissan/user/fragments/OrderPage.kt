package com.shrikissan.user.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cashfree.pg.CFPaymentService.*
import com.shrikissan.user.CustomProgressDialog
import com.shrikissan.user.OrderCompletedActivity
import com.shrikissan.user.R
import com.shrikissan.user.adapters.OrdersAdapter
import com.shrikissan.user.databinding.FragmentOrderPageBinding
import com.shrikissan.user.models.Address
import com.shrikissan.user.models.CartItem
import com.shrikissan.user.models.Constants
import com.shrikissan.user.models.showToast
import com.shrikissan.user.network.Repository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.math.min

class OrderPage : Fragment() {
    private lateinit var binding: FragmentOrderPageBinding
    private lateinit var adapter: OrdersAdapter
    private val list = ArrayList<CartItem>()
    private var deliveryCharge: Int = 40
    private var itemsPrice: Int = 0
    private var totalPrice: Int = 40
    private var walletBalance = 0
    private lateinit var repo: Repository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOrderPageBinding.inflate(layoutInflater)
        binding.continueButton.setOnClickListener {
            initiateTransaction()
        }
        list.clear()
        list.addAll(Constants.orderList)
        adapter = OrdersAdapter(requireContext(), list, true, { click ->

        }, { review ->

        })
        binding.orderItemsList.layoutManager = LinearLayoutManager(requireContext())
        binding.orderItemsList.adapter = adapter
        repo = Repository(requireContext())
        list.forEach {
            itemsPrice += it.subProduct.product_cost * it.quantity
        }
        binding.delivery.text =
            deliveryCharge.toString()
        binding.totalPrice.text =
            requireContext().getString(R.string.rupee) + totalPrice
        repo.getDeliveryPrice {
            deliveryCharge = it
            totalPrice = itemsPrice + deliveryCharge
            if (binding.checkbox.isChecked) {
                totalPrice = deliveryCharge + itemsPrice - walletBalance
            }
            binding.delivery.text =
                deliveryCharge.toString()
            binding.totalPrice.text =
                requireContext().getString(R.string.rupee) + totalPrice
        }
        repo.getWalletAmount {
            walletBalance = min(it, deliveryCharge + itemsPrice)
            if (binding.checkbox.isChecked) {
                binding.wallet.text = getString(R.string.rupee) + "-$walletBalance"
                totalPrice = deliveryCharge + itemsPrice - walletBalance
            }
            binding.totalPrice.text =
                requireContext().getString(R.string.rupee) + totalPrice
        }
        binding.wallet.text = "0"
        binding.checkbox.setOnClickListener {
            if (binding.checkbox.isChecked) {
                binding.wallet.text =getString(R.string.rupee) +  "-$walletBalance"
                totalPrice = deliveryCharge + itemsPrice - walletBalance
                binding.totalPrice.text =
                    requireContext().getString(R.string.rupee) + totalPrice
            } else {
                binding.wallet.text = getString(R.string.rupee) + "0"
                totalPrice = deliveryCharge + itemsPrice
                binding.totalPrice.text =
                    requireContext().getString(R.string.rupee) + totalPrice
            }
        }
        return binding.root
    }

    private fun initiateTransaction() {
        val dialog = CustomProgressDialog(requireContext())
        dialog.show()
        repo.getPaymentToken(totalPrice) { it, id ->
            dialog.dismiss()
            if (it != null) {
                startPayment(it, id.toString(), "", (totalPrice).toString(),
                    Constants.number, Constants.name)
            } else {
                requireActivity().showToast("Unknown Error Occurred")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addressString = arguments?.getString("address") ?: ""
        val address = Json.decodeFromString<Address>(addressString)
        binding.name.text = address.name
        binding.lane.text = address.lane
        binding.landMark.text = address.landmark
        binding.state.text = address.city + "," + address.state + "," + address.pinCode
        binding.phoneNumber.text = address.phoneNumber
        binding.itemsPrice.text = itemsPrice.toString()

    }

    private fun startPayment(
        token: String,
        id: String,
        email: String,
        amount: String,
        number: String,
        name: String,
    ) {
        val map = HashMap<String, String>()
        map[PARAM_APP_ID] = Constants.appId
        map[PARAM_ORDER_ID] = id
        map[PARAM_ORDER_AMOUNT] = amount
        map[PARAM_CUSTOMER_NAME] = name
        //map[PARAM_CUSTOMER_EMAIL] = email
        map[PARAM_CUSTOMER_PHONE] = number

        getCFPaymentServiceInstance().doPayment(requireActivity(), map, token, "TEST")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE) {
            if (data != null) {
                val bundle = data.extras
                if (bundle != null) {
                    for (key in bundle.keySet()) {
                        if (bundle.getString(key) != null) {
                            Log.d("paymentResult", key + " : " + bundle.getString(key))
                        }
                    }
                    val status = bundle.get("txStatus")
                    val orderId = bundle.get("orderId").toString()
                    val refId = bundle.get("referenceId").toString()

                    when (status) {
                        "SUCCESS" -> {
                            paymentComplete(orderId, refId)
                        }
                        "CANCELLED" -> {
                            requireActivity().showToast("Payment Failed")
                        }
                        else -> {
                            requireActivity().showToast("Payment Failed")
                        }
                    }
                } else {
                    requireActivity().showToast("Payment Failed")
                }
            }
        }
    }
    private fun paymentComplete(orderId: String, refId: String) {
        val intent = Intent(requireContext(), OrderCompletedActivity::class.java)
        intent.putExtra("orderId", orderId)
        intent.putExtra("refId", refId)
        startActivity(intent)
        requireActivity().finish()
    }

}