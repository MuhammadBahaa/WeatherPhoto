package muhammad.bahaa.robustatask.ui.home

import androidx.lifecycle.LiveData
import muhammad.bahaa.robustatask.ui.base.BaseViewModel
import muhammad.bahaa.robustatask.utils.SingleLiveEvent

class HomeViewModel : BaseViewModel() {

    private val _takePhotoEvent = SingleLiveEvent<Unit>()
    val takePhotoEvent: LiveData<Unit>
        get() = _takePhotoEvent

    fun onTakePhotoButtonClicked(){
        _takePhotoEvent.call()
    }
}