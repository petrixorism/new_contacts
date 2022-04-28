package uz.gita.contacts.data.model.request

data class AddContactRequest(
    val firstName: String,
    val lastName: String,
    val phone: String
)
