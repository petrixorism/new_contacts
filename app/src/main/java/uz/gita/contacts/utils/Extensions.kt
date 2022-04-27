package uz.gita.contacts.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import uz.gita.contacts.app.App
import uz.gita.contacts.data.model.response.ContactResponse
import uz.gita.contacts.data.source.local.db.ContactEntity


fun Fragment.showToast(message: String) {
    Toast.makeText(App.instance, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.makeVisibleOrGone(view: View, isVisible: Boolean) {
    if (isVisible) view.visibility = VISIBLE
    else view.visibility = GONE
}

fun ContactEntity.toContactResponse(): ContactResponse =
    ContactResponse(id, firstName, lastName, phone)

fun ContactResponse.toContactEntity(): ContactEntity =
    ContactEntity(id, firstName, lastName, phone)
