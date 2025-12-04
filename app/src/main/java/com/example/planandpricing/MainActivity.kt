package com.example.planandpricing

import android.os.Bundle
import android.widget.Button

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.ppbtn)

       btn.setOnClickListener {
           val bottomPlanAndPriceFragment = BottomPlanAndPriceFragment()
           bottomPlanAndPriceFragment.show(supportFragmentManager, bottomPlanAndPriceFragment.tag)
       }


    }
}