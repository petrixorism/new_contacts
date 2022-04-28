package uz.gita.contacts.data.model

import com.google.gson.Gson
import uz.gita.contacts.data.model.response.ContactResponse
import javax.xml.transform.Result

sealed class ResultData<T> {

    class Success<T>(val data: T) : ResultData<T>()
    class Message<T>(val message: String) : ResultData<T>()
    class Error<T>(val error: Throwable) : ResultData<T>()

}
