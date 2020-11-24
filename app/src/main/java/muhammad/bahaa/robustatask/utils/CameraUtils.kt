package muhammad.bahaa.robustatask.utils

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import muhammad.bahaa.robustatask.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun Activity.openCamera(file: File, reqCode: Int? = null) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
        putExtra(
                MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
                this@openCamera,
                BuildConfig.APPLICATION_ID + ".provider",
                file
        )
        )

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            val photoUri = FileProvider.getUriForFile(
                    this@openCamera,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file
            )

            clipData = ClipData.newRawUri("", photoUri)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    if (isCameraPermissionGranted() && intent.resolveActivity(packageManager) != null) {

        if (packageManager != null && intent.resolveActivity(packageManager) != null) startActivityForResult(
                intent,
                REQUEST_CAMERA
        )
    } else if (!isCameraPermissionGranted() && reqCode != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    reqCode
            )
        }
    }
}

private fun Activity.isCameraPermissionGranted(): Boolean {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
    )
        return false
    return true
}

val Context.fileToStoreCameraImg: File
    get() = with(
            "IMG_${
                SimpleDateFormat(
                        IMG_FILE_DATE_FORMAT,
                        Locale.getDefault()
                ).format(Date())
            }"
    ) {
        File.createTempFile(
                this,
                IMG_FILE_EXT,
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
    }