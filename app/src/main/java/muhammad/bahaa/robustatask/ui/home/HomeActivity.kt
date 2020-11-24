package muhammad.bahaa.robustatask.ui.home


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import muhammad.bahaa.robustatask.R
import muhammad.bahaa.robustatask.databinding.ActivityHomeBinding
import muhammad.bahaa.robustatask.ui.base.BaseActivity
import muhammad.bahaa.robustatask.ui.photo_preview.PhotoActivity
import muhammad.bahaa.robustatask.utils.*
import java.io.File

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(), PhotoClickCallback {

    override val rootView: Int
        get() = R.layout.activity_home
    override val vMType: Class<HomeViewModel> = HomeViewModel::class.java

    lateinit var imageFile: File

    override fun attachViewModelToView() {
        setObservers()
        initializeDataBindingVariables()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResume()
    }

    private fun initializeAdapter(images: List<String>) {
        val recyclerView = viewDataBinding.recyclerViewHistory
        recyclerView.adapter = HomeAdapter(images.toMutableList(), this)
        recyclerView.layoutManager =
                GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
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

        val photosListObserver = Observer<List<String>> {
            initializeAdapter(it)
        }

        viewModel.takePhotoEvent.observe(this, openCameraObserver)
        viewModel.photosList.observe(this, photosListObserver)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            val intent = Intent(this, PhotoActivity::class.java)
            intent.putExtra(INTENT_KEY_IMAGE_URI, Uri.fromFile(imageFile))
            intent.putExtra(INTENT_KEY_SAVE_MODE, true)
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

    override fun onImageClicked(imageUri: Uri) {
        val intent = Intent(this, PhotoActivity::class.java)
        intent.putExtra(INTENT_KEY_IMAGE_URI, imageUri)
        startActivity(intent)
    }

}