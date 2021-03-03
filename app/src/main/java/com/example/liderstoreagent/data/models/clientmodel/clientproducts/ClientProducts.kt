package com.example.liderstoreagent.data.models.clientmodel.clientproducts

data class ClientProducts(
    val created_date: String,
    val deadline: Any,
    val debt: String,
    val id: Int,
    val price: String,
    val product: Product,
    val quantity: String,
    val status: String,
    val updated_date: String
)