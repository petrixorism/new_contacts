package uz.gita.contacts.ui.viewmodel

import androidx.lifecycle.LiveData

interface MainViewModel {

    val splashLiveData: LiveData<Boolean>

    fun splash()

}