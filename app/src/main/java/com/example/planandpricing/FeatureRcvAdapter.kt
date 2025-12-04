package com.example.planandpricing

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planandpricing.databinding.CardPlanCardBinding

class FeatureRcvAdapter(
    val featureList: List<PlanAndPricingDataModel.Data.Package.Feature>
):  RecyclerView.Adapter<FeatureRcvAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val  view = CardPlanCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
       holder.bind(featureList[position],position)

    }

    override fun getItemCount(): Int {
        return 6
    }

    inner class  ViewHolder(val binding: CardPlanCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model: PlanAndPricingDataModel.Data.Package.Feature, position: Int){
            with(binding){
//                featureName.text = model.featureName
//                quantity.text = model.quantity.toString()
                featureName.text = featureList[position].featureName

                Log.d("sss", "${featureList.size}")

                if(featureList[position].featureName!="Application Insights")budgetCard.visibility = View.GONE



                if(featureList[position].quantity==1){
                    quantity.visibility = View.GONE
                    okIv.visibility = View.VISIBLE
                }
                else{

                    okIv.visibility = View.GONE
                    quantity.text = if (featureList[position].quantity==-1)"Unlimited" else featureList[position].quantity.toString()
                }

            }
        }

    }
}