package com.example.myapplication.repository.model

data class Country(
    val name: Name,
    val region: String,
    val area: Int,
    val flags: Flag
)

data class Name(
    val common: String,
    val official: String
)
