package io.aklinker1.files.common.glide

import android.os.Environment
import android.util.Log
import android.util.Size
import java.io.FileOutputStream
import java.io.IOException

object BitmapUtils {

  fun saveBitmapToFile(bitmap: android.graphics.Bitmap, filename: String) {
    val newFile = java.io.File(Environment.getExternalStorageDirectory(), filename)
    Log.d("fixate", "writing bitmap to file: " + newFile.path)
    var out: FileOutputStream? = null
    try {
      var created = false
      if (!newFile.exists()) created = newFile.createNewFile()
      Log.d("fixate", "Created bitmap's file? $created")
      out = FileOutputStream(newFile)
      bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 0, out) // bmp is your Bitmap instance
      // PNG is a lossless format, the compression factor (100) is ignored
    } catch (e: Exception) {
      e.printStackTrace()
    } finally {
      try {
        out?.close()
      } catch (e: IOException) {
        e.printStackTrace()
      }

    }
  }

  fun centerCrop(fit: Size, bitmap: Size): Size {
    return centerCrop(fit.width, fit.height, bitmap.width, bitmap.height)
  }

  fun centerCrop(fitWidth: Int, fitHeight: Int, bitmapWidth: Int, bitmapHeight: Int): Size {
    val scaledWidth = java.lang.Math.round(bitmapWidth / bitmapHeight.toFloat() * fitHeight)
    val scaledHeight = java.lang.Math.round(bitmapHeight / bitmapWidth.toFloat() * fitWidth)
    return if (scaledWidth < fitWidth)
      Size(fitWidth, scaledHeight)
    else
      Size(scaledWidth, fitHeight)
  }

  fun getCenterCropScaleFactorSize(fit: Size, bitmap: Size): Float {
    return getCenterCropScaleFactorSize(fit.width, fit.height, bitmap.width, bitmap.height)
  }

  fun getCenterCropScaleFactorSize(fitWidth: Int, fitHeight: Int, bitmapWidth: Int, bitmapHeight: Int): Float {
    val croppedSize = centerCrop(fitWidth, fitHeight, bitmapWidth, bitmapHeight)
    return if (croppedSize.width == fitWidth) {
      croppedSize.width / bitmapWidth.toFloat()
    } else {
      croppedSize.height / bitmapHeight.toFloat()
    }
  }

}
