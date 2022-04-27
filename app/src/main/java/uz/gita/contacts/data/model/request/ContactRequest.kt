package uz.gita.contacts.data.model.request

data class ContactRequest(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val phone: String
)
