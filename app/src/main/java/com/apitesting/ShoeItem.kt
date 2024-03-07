package com.apitesting

import com.google.gson.annotations.SerializedName

data class ShoeItem(
    @SerializedName("shoes")
    val shoes: List<Shoe>
)
data class Shoe(
    @SerializedName("id")
    val id:Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String
)
