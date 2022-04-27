package uz.gita.contacts.ui.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.contacts.data.source.local.SharedPref
import uz.gita.contacts.ui.viewmodel.MainViewModel


class MainViewModelImpl : ViewModel(), MainViewModel {
    override val splashLiveData = MutableLiveData<Boolean>()

    override fun splash() {
        splashLiveData.postValue(SharedPref.getInstance().isLogedIn)
    }
}