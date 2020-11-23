package muhammad.bahaa.robustatask.ui.save_photo

import androidx.lifecycle.LiveData
import muhammad.bahaa.robustatask.ui.base.BaseViewModel
import muhammad.bahaa.robustatask.utils.SingleLiveEvent

class SavePhotoViewModel : BaseViewModel(){

    private val _savePhotoEvent = SingleLiveEvent<Unit>()
    val savePhotoEvent: LiveData<Unit>
        get() = _savePhotoEvent

    fun onSavePhotoButtonClicked(){
        _savePhotoEvent.call()
    }
}