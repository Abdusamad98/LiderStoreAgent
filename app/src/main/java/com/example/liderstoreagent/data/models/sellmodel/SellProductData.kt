package com.example.liderstoreagent.data.models.sellmodel

data class SellProductData(
    val price: String,
    val quantity: String,
    val status: String = "",
    val deadline: String = "",
    val client: Int,
    val product: Int
)
