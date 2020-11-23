package muhammad.bahaa.robustatask.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.format.DateFormat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

object BitmapUtils {
    fun saveImageUri(image: Bitmap?, context: Context): Uri? {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val values =
                contentValues()
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/RobustaWeather")
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            val uri =
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            uri?.takeIf { image != null }?.apply {
                saveImageToStream(
                    image!!,
                    context.contentResolver.openOutputStream(uri)
                )
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
            }
            return uri
        } else {
            val rootFilePath =
                Environment.getExternalStorageDirectory().absolutePath.toString() +
                        "/Pictures/RobustaWeather"
            val rootDir = File(rootFilePath)
            if (!rootDir.exists()) rootDir.mkdirs()
            val file = File(rootDir,
                fileSuffix()
            )
            val fOut = FileOutputStream(file)
            saveImageToStream(
                image,
                fOut
            )
            return Uri.fromFile(file)
        }
    }

    private fun contentValues(): ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap?, outputStream: OutputStream?) {
        outputStream?.apply {
            try {
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fileSuffix(): String {
        return DateFormat.format("dd-MM-yyyy-HH-mm-ss", Date()).toString() + ".png"
    }
}