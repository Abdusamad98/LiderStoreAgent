package com.example.liderstoreagent.data.models.clientmodel

import java.io.File

//(dokon nomi,
// mas'ul shaxs,
//tel raqami (ikkita nomer olishi) faqat bittasi requared*,
// geo location,
// dokon rasmi,
// taxminiy qancha oborot qilishi)


class AddClientData(
    val marketName: String,
    val responsiblePerson: String,
    val phoneNumber1: String,
    val phoneNumber2: String = "",
    val latitude: String,
    val longitude: String,
    val image: File? = null,
    val assumptionValue: Double
)