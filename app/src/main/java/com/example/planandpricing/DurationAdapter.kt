package com.example.planandpricing

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planandpricing.databinding.DurationCardBinding

class DurationAdapter(val context: Context?) : RecyclerView.Adapter<DurationAdapter.DurationAdapterHolder>(){

    private var  durationList: MutableList<PlanAndPricingDataModel.Data> =mutableListOf()

    var onClickDuration:((model: PlanAndPricingDataModel.Data, position: Int)->Unit)? = null

    private var selectedPosition = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DurationAdapterHolder {
        val view: DurationCardBinding = DurationCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DurationAdapterHolder(view)
    }

    override fun onBindViewHolder(
        holder: DurationAdapterHolder,
        position: Int
    ) {
        holder.bind(durationList[position], position)
        }



    override fun getItemCount(): Int {
        return durationList.size
    }

    inner class DurationAdapterHolder(val binding: DurationCardBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SuspiciousIndentation", "ResourceAsColor", "NotifyDataSetChanged")
        fun bind(model: PlanAndPricingDataModel.Data, position: Int){
            with(binding){
            val plan = durationList[position].duration
                offTv.text = if(plan=="Yearly")"75% OFF" else "50% OFF"

            duration.text = plan
                if(plan == "Monthly"){
                    offTv.visibility= View.GONE
                    mainCard.setCardBackgroundColor(R.color.white)
                }
                if (position == selectedPosition) {
                    durationCard.setCardBackgroundColor(
                        context?.getColor(R.color.selectedCard) ?: 0
                    )

                    durationCard.strokeColor =
                        context?.getColor(R.color.updatedBorderColor) ?: 0

                    durationCard.strokeWidth =
                        (2 * context?.resources?.displayMetrics?.density!!).toInt() // 2dp border

                    duration.setTextColor(
                        context.getColor(R.color.defaultColor)
                    )

                } else {
                    durationCard.setCardBackgroundColor(
                        context?.getColor(R.color.white) ?: 0
                    )

                    durationCard.strokeWidth = 0

                    duration.setTextColor(
                        context?.getColor(R.color.ash_100) ?: 0
                    )
                }


                mainCard.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
                onClickDuration?.invoke( model, position)
            }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initData(data: List<PlanAndPricingDataModel.Data>) {
        durationList.clear()
        durationList.addAll(data)
        notifyDataSetChanged()
    }
 }

