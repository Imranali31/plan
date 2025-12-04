package com.example.planandpricing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class HomeFragment : Fragment() {
    val bottomPlanAndPriceFragment = BottomPlanAndPriceFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btn = view.findViewById<Button>(R.id.ppbtn)

        btn.setOnClickListener {

            bottomPlanAndPriceFragment.show(parentFragmentManager, bottomPlanAndPriceFragment.tag)
        }
        return view

    }

    override fun onDestroy() {
        super.onDestroy()
        bottomPlanAndPriceFragment.dismiss()
    }


}