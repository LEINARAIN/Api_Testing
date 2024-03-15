package com.apitesting

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShoeService {

    @GET("api/shoes")
    suspend fun getShoeItem(): Response<ShoeItem>

    @GET("api/search")
    suspend fun searchShoes(@Query("query") query: String): Response<ShoeItem>

}