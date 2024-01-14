package com.example.myapplication.repository.model

data class CountryResponse(
    val name: Name,
    val independent: String,
    val unMember: String,
    val capital: List<String>,
    val flags: Flag
)

data class Flag(
    val png: String
)
