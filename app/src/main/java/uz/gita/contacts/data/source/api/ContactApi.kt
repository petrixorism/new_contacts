package uz.gita.contacts.data.source.api

import retrofit2.Response
import retrofit2.http.*
import uz.gita.contacts.data.model.MainResponse
import uz.gita.contacts.data.model.request.ContactRequest
import uz.gita.contacts.data.model.response.ContactResponse

interface ContactApi {


    @GET("/api/v1/contact ")
    suspend fun getContactList(): Response<MainResponse<List<ContactResponse>>>

    @POST("/api/v1/contact")
    suspend fun addContact(@Body data: ContactRequest): Response<MainResponse<ContactResponse>>

    @PUT("/api/v1/contact")
    suspend fun editContact(@Body data: ContactResponse): Response<MainResponse<ContactResponse>>

    @DELETE("/api/v1/contact")
    suspend fun deleteContact(@Query("id") id: Int): Response<MainResponse<ContactResponse>>



}