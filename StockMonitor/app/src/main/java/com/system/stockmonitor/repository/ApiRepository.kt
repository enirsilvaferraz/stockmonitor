package com.system.stockmonitor.repository

import com.google.gson.GsonBuilder
import com.system.stockmonitor.models.Credential
import com.system.stockmonitor.models.StockData
import com.system.stockmonitor.models.StockInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ApiRepository {

    suspend fun getCredentials() = suspendCoroutine<Credential> { continuation ->

        val webservice = Retrofit.Builder()
            .baseUrl("https://www.bussoladoinvestidor.com.br/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(ApiService::class.java)

        webservice.getCredentials().enqueue(object : Callback<Credential> {

            override fun onFailure(call: Call<Credential>, t: Throwable) {
                continuation.resumeWithException(t)
            }

            override fun onResponse(call: Call<Credential>, response: Response<Credential>) {
                val value = response.body()
                if (response.isSuccessful && value != null) {
                    continuation.resume(value)
                } else {
                    continuation.resumeWithException(Exception("Credential failure"))
                }
            }
        })
    }

    suspend fun getStockData(
        stockSymbol: String,
        startDate: String,
        endDate: String,
        credential: Credential
    ) =
        suspendCoroutine<StockData> { continuation ->

            val webservice = Retrofit.Builder()
                .baseUrl("https://marketdata-streamer.smarttbot.com/data/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(ApiService::class.java)

            webservice.getStockData(
                stockSymbol,
                credential.username,
                credential.data_type,
                credential.origin,
                credential.expiration_date,
                credential.token,
                startDate,
                endDate
            ).enqueue(object : Callback<List<StockData>> {

                override fun onFailure(call: Call<List<StockData>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(
                    call: Call<List<StockData>>,
                    response: Response<List<StockData>>
                ) {

                    val value = response.body()
                    if (response.isSuccessful && value != null) {
                        continuation.resume(value[0])
                    } else {
                        continuation.resumeWithException(Exception("Stock data failure"))
                    }
                }
            })
        }

    suspend fun getStockInfo(stockSymbol: String, credential: Credential) =
        suspendCoroutine<StockInfo> { continuation ->

            val webservice = Retrofit.Builder()
                .baseUrl("https://marketdata-streamer.smarttbot.com/data/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(ApiService::class.java)

            webservice.getStockInfo(
                stockSymbol,
                credential.username,
                credential.data_type,
                credential.origin,
                credential.expiration_date,
                credential.token
            ).enqueue(object : Callback<List<StockInfo>> {

                override fun onFailure(call: Call<List<StockInfo>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(
                    call: Call<List<StockInfo>>,
                    response: Response<List<StockInfo>>
                ) {

                    val value = response.body()
                    if (response.isSuccessful && value != null && value.isNotEmpty()) {
                        continuation.resume(value[0])
                    } else {
                        continuation.resume(StockInfo("", "", ""))
                    }
                }
            })
        }
}