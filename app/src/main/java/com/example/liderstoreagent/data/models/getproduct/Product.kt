package com.example.liderstoreagent.data.models.getproduct

data class Product(
    val category: Category,
    val id: Int,
    val  quantity :Double,
    val name: String,
    val product_type: String,
    val provider: Provider,
    val unit: String
)