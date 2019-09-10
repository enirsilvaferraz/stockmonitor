package com.system.stockmonitor.models

data class Credential(
    val username: String,
    val data_type: String,
    val origin: String,
    val expiration_date: Long,
    val token: String
)