package muhammad.bahaa.robustatask.ui.save_photo

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import muhammad.bahaa.robustatask.R
import muhammad.bahaa.robustatask.databinding.ActivitySavePhotoBinding
import muhammad.bahaa.robustatask.ui.base.BaseActivity
import muhammad.bahaa.robustatask.utils.INTENT_KEY_IMAGE_URI
import muhammad.bahaa.robustatask.utils.REQUEST_STORAGE
import muhammad.bahaa.robustatask.utils.checkLocationRequestPermissionState

class SavePhotoActivity : BaseActivity<SavePhotoViewModel, ActivitySavePhotoBinding>(), LocationListener {

    override val rootView: Int
        get() = R.layout.activity_save_photo
    override val vMType: Class<SavePhotoViewModel> = SavePhotoViewModel::class.java
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.M)
    override fun attachViewModelToView() {
        initializeDataBindingVariables()
        displayCapturedImage()
        setObservers()
        checkLocationRequestPermissionState(this)
    }

    private fun initializeDataBindingVariables() {
        viewDataBinding.savePhotoViewModel = viewModel
        viewDataBinding.lifecycleOwner = this
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setObservers() {
        val savePhotoObserver = Observer<Unit> {
            checkRequestPermissionState()
        }
        viewModel.savePhotoEvent.observe(this, savePhotoObserver)
    }

    private fun displayCapturedImage() {
        intent.getParcelableExtra<Uri>(INTENT_KEY_IMAGE_URI)?.let {
            viewDataBinding.imageView.setImageURI(it)
        }
    }

    private fun saveImageToStorage() {
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkRequestPermissionState() {
        when (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )) {
            PackageManager.PERMISSION_DENIED -> {
                requestStoragePermission()
            }
            PackageManager.PERMISSION_GRANTED -> {
                saveImageToStorage()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestStoragePermission() {
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_STORAGE
        )
    }

    override fun onLocationFetched(lat: String, lon: String) {

    }

}