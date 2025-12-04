package com.example.planandpricing


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PlanAndPricingDataModel(
    @SerializedName("common")
    val common: Any,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int
) {
    @Keep
    data class Data(
        @SerializedName("duration")
        val duration: String,
        @SerializedName("durationCount")
        val durationCount: Int,
        @SerializedName("packages")
        val packages: List<Package>
    ) {
        @Keep
        data class Package(
            @SerializedName("discount")
            val discount: Double,
            @SerializedName("discountPercentage")
            val discountPercentage: String,
            @SerializedName("feature")
            val feature: List<Feature>,
            @SerializedName("hasPackage")
            val hasPackage: Int,
            @SerializedName("packageId")
            val packageId: Int,
            @SerializedName("packageName")
            val packageName: String,
            @SerializedName("price")
            val price: Double,
            @SerializedName("serviceId")
            val serviceId: Int
        ) {
            @Keep
            data class Feature(
                @SerializedName("featureId")
                val featureId: Int,
                @SerializedName("featureName")
                val featureName: String,
                @SerializedName("quantity")
                val quantity: Int
            )
        }
    }
}