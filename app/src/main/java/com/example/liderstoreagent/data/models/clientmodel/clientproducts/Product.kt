package com.example.liderstoreagent.data.models.clientmodel.clientproducts

data class Product(
    val category: Int,
    val id: Int,
    val name: String,
    val product_type: String,
    val provider: Int,
    val unit: String
)