package muhammad.bahaa.robustatask.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import muhammad.bahaa.robustatask.ui.photo_preview.LocationListener

@RequiresApi(Build.VERSION_CODES.M)
fun Activity.checkLocationRequestPermissionState(listener: LocationListener) {
    when (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
        PackageManager.PERMISSION_DENIED -> {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
        }
        PackageManager.PERMISSION_GRANTED -> {
            getCurrentLocation(listener)
        }
    }
}


@SuppressLint("MissingPermission")
fun Activity.getCurrentLocation(listener: LocationListener) {

    var fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    fusedLocationClient.lastLocation
            .addOnSuccessListener {
                listener.onLocationFetched(it.latitude.toString(), it.longitude.toString())
            }
}