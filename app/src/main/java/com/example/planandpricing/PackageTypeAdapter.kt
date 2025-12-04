package com.example.planandpricing

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.planandpricing.databinding.PlanCardBinding


class PackageTypeAdapter(
    val context: Context,
    private val onUpgradeClick: (PlanAndPricingDataModel.Data.Package) -> Unit
): RecyclerView.Adapter<PackageTypeAdapter.PlanRcvAdapterHolder>(){

    private  var packagesList : MutableList<PlanAndPricingDataModel.Data.Package> = mutableListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlanRcvAdapterHolder {
        val view = PlanCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlanRcvAdapterHolder(view)
    }

    override fun onBindViewHolder(
        holder: PlanRcvAdapterHolder,
        position: Int
    ) {
        holder.bind(packagesList[position],position)
    }

    override fun getItemCount(): Int {
        return packagesList.size
    }

    inner class PlanRcvAdapterHolder(val binding: PlanCardBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SuspiciousIndentation", "SetTextI18n")
        fun bind(model: PlanAndPricingDataModel.Data.Package, position: Int){
            Log.d("onBindViewHolder: ","onBindViewHolder: ${packagesList}")
            with(binding){
                val pack = packagesList[position]
                regularPrice.visibility = if(pack.discount == 0.0)View.GONE else View.VISIBLE
                dPCard.visibility = if(pack.discount == 0.0)View.GONE else View.VISIBLE
                packageName.text = pack.packageName
                offerPrice.text = "৳${pack.price}"
                regularPrice.text = "৳${(pack.discount+ pack.price)}"
                regularPrice.paintFlags = regularPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                discountPercentage.text = pack.discountPercentage
                upgradeBtn.text = "Upgrade Plan (৳${pack.price})"
                if(pack.packageName != "Standard")recommendedCard.visibility = View.GONE

                    val requiredIds = listOf(1,7,4,10,2,3)

                    val filteredList = requiredIds.mapNotNull { id ->
                        packagesList[position].feature.find { it.featureId == id }
                    }

                    binding.planRcv.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                        adapter = FeatureRcvAdapter(filteredList)
                    }

                    seeMore.setOnClickListener {
                        Toast.makeText(context,"We will implement this feature soon", Toast.LENGTH_LONG).show()
                    }

                    upgradeBtn.setOnClickListener {
                        onUpgradeClick(packagesList[position])
                    }
                }


        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun initData(data: List<PlanAndPricingDataModel.Data.Package>) {
        packagesList.clear()
        packagesList.addAll(data)
        notifyDataSetChanged()
    }
    fun updateData(data: List<PlanAndPricingDataModel.Data.Package>) {
        packagesList.clear()
        packagesList.addAll(data)
        notifyDataSetChanged()
    }




}