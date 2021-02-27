package com.example.liderstoreagent.data.models.reportmodel

import java.io.File

data class ReportData(
    val comment: String = "",
    val image: File? = null,
    val sale_agent: Int
)