package uz.gita.contacts.data.model.response

import com.airbnb.lottie.parser.moshi.JsonReader

data class TokenResponse(
    val token: String? = null,
    val phone: String? = null,
    val message: String? = null
)

fun TokenResponse.toMessage(): String = "$token $phone $message"