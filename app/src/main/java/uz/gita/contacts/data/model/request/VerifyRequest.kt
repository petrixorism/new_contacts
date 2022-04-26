package uz.gita.contacts.data.model.request

data class VerifyRequest(
    val phone: String,
    val code: String
)
