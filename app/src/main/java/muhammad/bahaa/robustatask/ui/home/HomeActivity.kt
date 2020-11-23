package muhammad.bahaa.robustatask.ui.home


import android.content.pm.PackageManager
import androidx.lifecycle.Observer
import muhammad.bahaa.robustatask.R
import muhammad.bahaa.robustatask.databinding.ActivityHomeBinding
import muhammad.bahaa.robustatask.ui.base.BaseActivity
import muhammad.bahaa.robustatask.utils.REQUEST_CAMERA
import muhammad.bahaa.robustatask.utils.fileToStoreCameraImg
import muhammad.bahaa.robustatask.utils.openCamera

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {

    override val rootView: Int
        get() = R.layout.activity_home
    override val vMType: Class<HomeViewModel> = HomeViewModel::class.java

    override fun attachViewModelToView() {
        setObservers()
        initializeDataBindingVariables()
    }

    private fun initializeDataBindingVariables() {
        viewDataBinding.homeViewModel = viewModel
        viewDataBinding.lifecycleOwner = this
    }

    private fun setObservers() {

        val openCameraObserver = Observer<Unit> {
            openCamera(fileToStoreCameraImg, REQUEST_CAMERA)
        }

        viewModel.takePhotoEvent.observe(this, openCameraObserver)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (grantResults.isNotEmpty() || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.openCamera(fileToStoreCameraImg)

                }
            }
        }
    }
}