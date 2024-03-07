package com.apitesting

import retrofit2.Response
import retrofit2.http.GET

interface ShoeService {

    @GET("public/api")
    suspend fun getShoeItem(): Response<ShoeItem>

}