package uz.gita.contacts.data.model


data class MainResponse<T>(
    val data: T? = null,
    val message: String? = null
)