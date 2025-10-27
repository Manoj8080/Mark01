package com.nukkadshops.mark01

data class LoginRequest(
    val username : String,
    val password : String
)

data class LoginResponse(
    val message: String,
    val username : String,
    val languages: List<Language>
)

data class Language(
    val id: Int,
    val language_name: String
)

