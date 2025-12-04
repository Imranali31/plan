package com.example.planandpricing

import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiServices {
    @FormUrlEncoded
    @POST("CandidateMonetization/Package-v03")
    fun getAllPackages(
        @Field("userId") userId: String? = "",
        @Field("decoderId") decoderId: String? = "",
        @Field("deviceType") deviceType: String? = "app"
    ): Call<PlanAndPricingDataModel>
    companion object Factory {
        private var retrofit: Retrofit? = null
        @Synchronized
        fun create(): ApiServices? {
            retrofit?:synchronized(this){
                retrofit = buildRetrofit()
            }

            return retrofit?.create(ApiServices::class.java)

        }

        private  fun buildRetrofit(): Retrofit? {
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(OkHttpProfilerInterceptor())
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40,TimeUnit.SECONDS)
                .connectTimeout(40,TimeUnit.SECONDS)

            okHttpClientBuilder.addInterceptor(OkHttpProfilerInterceptor())

            val okHttpClient = okHttpClientBuilder.build()

            return Retrofit.Builder().apply {
                baseUrl("https://api.bdjobs.com/mybdjobs/v1/")
                addConverterFactory(GsonConverterFactory.create(gson))
                client(okHttpClient)
            }.build()
        }
    }
}