package muhammad.bahaa.robustatask.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import muhammad.bahaa.robustatask.data.preference.PreferenceManager
import muhammad.bahaa.robustatask.ui.base.BaseViewModel
import muhammad.bahaa.robustatask.utils.SingleLiveEvent

class HomeViewModel : BaseViewModel() {

    private val _takePhotoEvent = SingleLiveEvent<Unit>()
    val takePhotoEvent: LiveData<Unit>
        get() = _takePhotoEvent

    private val _photosList = MutableLiveData<List<String>>()
    val photosList: LiveData<List<String>>
        get() = _photosList

    init {
        getPhotosHistory()
    }

    fun onViewResume() {
        getPhotosHistory()
    }

    fun onTakePhotoButtonClicked() {
        _takePhotoEvent.call()
    }

    private fun getPhotosHistory() {
        val list: ArrayList<String>? = PreferenceManager.getPhotosList()
        list?.let {
            if (list.isNotEmpty()) _photosList.value = list
        }
    }
}