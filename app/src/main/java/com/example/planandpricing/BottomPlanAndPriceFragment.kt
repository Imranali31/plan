package com.example.planandpricing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planandpricing.databinding.FragmentBottomPlanAndPriceBinding
import com.example.planandpricing.databinding.ShimmerPlanCardBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.facebook.shimmer.ShimmerFrameLayout


class BottomPlanAndPriceFragment : BottomSheetDialogFragment() {
    private var  durationList: MutableList<PlanAndPricingDataModel.Data> =mutableListOf()
    private var tempPackagesTypeList : MutableList<PlanAndPricingDataModel.Data.Package> = mutableListOf()
    private var packagesTypeList : MutableList<PlanAndPricingDataModel.Data.Package> = mutableListOf()
    private var packageFeatureList : MutableList<PlanAndPricingDataModel.Data.Package.Feature> = mutableListOf()
    private lateinit var durationAdapter: DurationAdapter
    private lateinit var packageTypeAdapter: PackageTypeAdapter
    lateinit var binding: FragmentBottomPlanAndPriceBinding
    lateinit var  shimmerLayout : ShimmerPlanCardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomPlanAndPriceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shimmerLayout = ShimmerPlanCardBinding.bind(binding.shimmer.root)
        shimmerLayout.shimmerPlan.startShimmer()
        shimmerLayout.shimmerPlan.visibility = View.VISIBLE
        binding.parent.visibility = View.GONE
        loadPlanAndPrice()
    }

    private fun loadPlanAndPrice() {

        ApiServices.create()?.getAllPackages("4361771","S8A8Qw")
            ?.enqueue(object : Callback<PlanAndPricingDataModel>{
                override fun onResponse(
                    call: Call<PlanAndPricingDataModel?>,
                    response: Response<PlanAndPricingDataModel?>
                ) {
                    if (response.isSuccessful){
                        val data = response.body()?.data
                        durationList.clear()

                        data?.forEach { packages->
                            if(packages.durationCount != 6){
                                durationList.add(packages)
                            }
                        }
                        tempPackagesTypeList.addAll(durationList.first().packages)

                        packagesTypeList.clear()
                        tempPackagesTypeList.forEach { packages->
                            if(packages.packageId != 1){
                                packagesTypeList.add(packages)
                            }
                        }

                        packagesTypeList.forEach { packages->
                            packageFeatureList.addAll(packages.feature)
                        }

                        Log.d("onResponse: ","onResponse: ${packagesTypeList}")

                        shimmerLayout.shimmerPlan.stopShimmer()
                        shimmerLayout.shimmerPlan.visibility = View.GONE
                        binding.parent.visibility = View.VISIBLE

                        setupClick()
                    }
                }

                override fun onFailure(
                    call: Call<PlanAndPricingDataModel?>,
                    t: Throwable
                ) {

                    shimmerLayout.shimmerPlan.stopShimmer()
                    shimmerLayout.shimmerPlan.visibility = View.GONE
                    Log.d("onResponse: ", "onFailure: jhgejfg3uy")
                }

                })

    }

    private  fun setupClick(){
        context?.let { nonNullContext->
            durationAdapter = DurationAdapter(nonNullContext)


            with(binding.durationRecycler){
                layoutManager = GridLayoutManager(context,3)
                adapter = durationAdapter
            }
            durationAdapter.initData(durationList)

            durationAdapter.onClickDuration = { model, position ->
                packagesTypeList.clear()
                model.packages.forEach { packages->
                    if(packages.packageId != 1){
                        packagesTypeList.add(packages)
                    }
                }
                packageTypeAdapter.updateData(packagesTypeList)
            }
            packageTypeAdapter = PackageTypeAdapter(nonNullContext) { clickedPackage ->

                val selectedDuration = durationList.find { duration ->
                    duration.packages.contains(clickedPackage)
                }
                val bundle = bundleOf(
                    "packageName" to clickedPackage.packageName,
                    "price" to clickedPackage.price,
                    "durationCount" to (selectedDuration?.durationCount ?: 1).toString()
                )
                findNavController().navigate(R.id.paymentFragment, bundle)
               dismiss()
            }
            packageTypeAdapter.initData(packagesTypeList)
            with(binding.rcv){
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = packageTypeAdapter
            }

        }
    }


    override fun onStart() {
        super.onStart()

        val dialog = dialog as? BottomSheetDialog ?: return
        val bottomSheet =
            dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                ?: return

        val behavior = BottomSheetBehavior.from(bottomSheet)

        val halfHeight = (resources.displayMetrics.heightPixels * 0.85f).toInt()

        bottomSheet.layoutParams.height = halfHeight
        bottomSheet.requestLayout()

        behavior.peekHeight = halfHeight
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED

        behavior.isHideable = true
        behavior.skipCollapsed = false
    }
}