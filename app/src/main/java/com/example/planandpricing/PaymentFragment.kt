package com.example.planandpricing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class PaymentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val packageName = arguments?.getString("packageName")
        val price = arguments?.getDouble("price")
        val durationCount = arguments?.getString("durationCount")

        val tvPackageName = view.findViewById<TextView>(R.id.packName)
        val tvPrice = view.findViewById<TextView>(R.id.price)
        val totalPrice = view.findViewById<TextView>(R.id.totalAmountTV)
        val subTotal = view.findViewById<TextView>(R.id.subTotal)
        val duration = view.findViewById<TextView>(R.id.duration)

        tvPackageName.text = packageName ?: "N/A"
        tvPrice.text = price?.toString() ?: "0"
        totalPrice.text = price?.toString() ?: "0"
        subTotal.text = price?.toString() ?: "0"
        duration.text = if (durationCount=="1") "1 month" else "$durationCount months"


    }

}