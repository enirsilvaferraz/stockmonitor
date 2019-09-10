package com.system.stockmonitor.repository

import com.system.stockmonitor.models.Credential
import com.system.stockmonitor.models.StockData
import com.system.stockmonitor.models.StockInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("nb/api/v1/chart/credentials")
    fun getCredentials(): Call<Credential>

    @GET("stocks/{stock}/")
    fun getStockInfo(
        @Path("stock") stock: String,
        @Query("username") username: String,
        @Query("data_type") data_type: String,
        @Query("origin") origin: String,
        @Query("expiration_date") expiration_date: Long,
        @Query("token") token: String
    ): Call<List<StockInfo>>

    @GET("candles/one/{stock}/")
    fun getStockData(
        @Path("stock") stock: String,
        @Query("username") username: String,
        @Query("data_type") data_type: String,
        @Query("origin") origin: String,
        @Query("expiration_date") expiration_date: Long,
        @Query("token") token: String,
        @Query("start_datetime") start_datetime: String,
        @Query("end_datetime") end_datetime: String
    ): Call<List<StockData>>

}