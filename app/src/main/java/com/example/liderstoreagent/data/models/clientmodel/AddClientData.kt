package com.example.liderstoreagent.data.models.clientmodel

import java.io.File

class AddClientData(
        val marketName: String,
        val address: String,
        val responsiblePerson: String,
        val phoneNumber1: String,
        val phoneNumber2: String = "",
        val latitude: Double,
        val longitude: Double,
        val image: File ,
        val assumptionValue: Int,
        val agentId: Int,
)