package com.example.helgather.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import androidx.exifinterface.media.ExifInterface
import java.io.IOException

object ImageUtil {
    @Throws(IOException::class)
    fun rotateImageIfRequired(context: Context, img: Bitmap, selectedImage: Uri): Bitmap {

        val input = context.contentResolver.openInputStream(selectedImage)
        val ei = if (Build.VERSION.SDK_INT > 23)
            ExifInterface(input!!)
        else
            ExifInterface(selectedImage.path!!)

        val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90f)

            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180f)

            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270f)

            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flipImage(img, true, false)

            ExifInterface.ORIENTATION_FLIP_VERTICAL -> flipImage(img, false, true)

            else -> img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }

    private fun flipImage(img: Bitmap, horizontal: Boolean, vertical: Boolean): Bitmap {
        val matrix = Matrix()
        matrix.preScale((if (horizontal) -1 else 1).toFloat(), (if (vertical) -1 else 1).toFloat())
        val flippedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return flippedImg
    }

}
