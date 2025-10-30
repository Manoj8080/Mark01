package com.nukkadshops.mark01

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class LoginRequest(
    val username : String,
    val password : String
)

data class LoginResponse(
    val userId: Int,
    val message: String,
    val username : String,
    val languages: List<Language>
)

@Parcelize
data class Language(
    val id: Int,
    val language_name: String
): Parcelable


