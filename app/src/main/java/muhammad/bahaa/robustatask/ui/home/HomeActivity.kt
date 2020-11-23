package muhammad.bahaa.robustatask.ui.home


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.Observer
import muhammad.bahaa.robustatask.R
import muhammad.bahaa.robustatask.databinding.ActivityHomeBinding
import muhammad.bahaa.robustatask.ui.base.BaseActivity
import muhammad.bahaa.robustatask.ui.save_photo.SavePhotoActivity
import muhammad.bahaa.robustatask.utils.*
import java.io.File

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {

    override val rootView: Int
        get() = R.layout.activity_home
    override val vMType: Class<HomeViewModel> = HomeViewModel::class.java

    lateinit var imageFile: File

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
            imageFile = fileToStoreCameraImg
            openCamera(imageFile, REQUEST_CAMERA)
        }
        viewModel.takePhotoEvent.observe(this, openCameraObserver)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            val intent = Intent(this, SavePhotoActivity::class.java)
            intent.putExtra(INTENT_KEY_IMAGE_URI, Uri.fromFile(imageFile))
            startActivity(intent)
        }

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