package io.aklinker1.files.common.glide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.pdf.PdfRenderer
import android.graphics.text.LineBreaker
import android.media.MediaMetadataRetriever
import android.os.Build
import android.os.ParcelFileDescriptor
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.util.Size
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import io.aklinker1.files.R
import io.aklinker1.files.common.utils.MathUtils
import io.aklinker1.files.common.utils.ResourceUtils
import io.aklinker1.files.common.utils.TextFileUtils
import io.aklinker1.livefs.LiveFile
import org.jaudiotagger.audio.AudioFileIO
import java.io.*
import kotlin.math.ceil
import kotlin.math.roundToInt

class LiveFileDataFetcher internal constructor(
  private val context: Context, private val model: LiveFile, val width: Int, val height: Int,
) : DataFetcher<InputStream> {

  override fun getDataClass(): Class<InputStream> {
    return InputStream::class.java
  }

  override fun getDataSource(): DataSource {
    return DataSource.LOCAL
  }

  private var mInputStream: InputStream? = null
  private val extensionInfo: FileInfo.ExtensionInfo =
    FileInfo.extensionInfos[model.extensionNoDot()] ?: FileInfo.defaultExtensionInfo(context)
  private val basicStream: InputStream
    get() = FileInputStream(model.path)

  private val textStream: InputStream?
    get() = getTextFileStream(ResourceUtils.attrColor(context, R.attr.backgroundColor),
      R.color.grey_500)

  private val videoStream: InputStream
    get() {
      val retriever = MediaMetadataRetriever()
      retriever.setDataSource(model.path)
      val thumbnail = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
      val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
      var randomMicrosecond: Long
      try {
        val microseconds = java.lang.Long.parseLong(duration ?: "0") * 1000L
        randomMicrosecond = MathUtils.randomLong(max = microseconds)
      } catch (e: Exception) {
        randomMicrosecond = 0
        Log.e("fixate", "Error getting random frame", e)
      }

      val original = retriever.getFrameAtTime(randomMicrosecond) ?: return bitmapToStream(thumbnail)
      retriever.release()
      val scaledSize = BitmapUtils.centerCrop(width, height, original.width, original.height)
      val scaledBitmap =
        Bitmap.createScaledBitmap(original, scaledSize.width, scaledSize.height, false)
      original.recycle()
      val play =
        ResourcesCompat.getDrawable(context.resources, R.drawable.preview_play, context.theme)
      val diameter = (width.coerceAtMost(height) / 2.5f).roundToInt()
      play?.setBounds((width - diameter) / 2,
        (height - diameter) / 2,
        width - (width - diameter) / 2,
        height - (height - diameter) / 2)
      val canvas = Canvas(thumbnail)
      canvas.drawBitmap(scaledBitmap,
        ((thumbnail.width - scaledBitmap.width) / 2).toFloat(),
        ((thumbnail.height - scaledBitmap.height) / 2).toFloat(),
        null)
      play?.draw(canvas)
      scaledBitmap.recycle()
      return bitmapToStream(thumbnail)
    }

  private val audioStream: InputStream
    get() {
      val audioFile = AudioFileIO.read(File(model.path))
      val bytes = audioFile.tag.firstArtwork.binaryData
      return ByteArrayInputStream(bytes)
    }

  private val apkStream: InputStream
    get() {
      Log.d("fixate", "Loading apk file icon")
      val manager = context.packageManager
      val info = manager.getPackageArchiveInfo(model.path, 0)!!
      info.applicationInfo.publicSourceDir = model.path
      info.applicationInfo.sourceDir = model.path
      val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
      val canvas = Canvas(bitmap)
      val background = AppCompatResources.getDrawable(context,
        extensionInfo.color!!) // TODO This was using a drawable, switch back?
      val backgroundSize = BitmapUtils.centerCrop(width,
        height,
        background!!.intrinsicWidth,
        background.intrinsicHeight)
      background.setBounds((width - backgroundSize.width) / 2,
        (height - backgroundSize.height) / 2,
        width + (backgroundSize.width - width) / 2,
        height + (backgroundSize.height - height) / 2)
      background.draw(canvas)
      val icon = info.applicationInfo.loadIcon(manager)
      icon.setBounds((width - height) / 2 + height / 4,
        height / 4,
        (width - height) / 2 + height * 3 / 4,
        height * 3 / 4)
      icon.draw(canvas)
      return bitmapToStream(bitmap)
    }

  private val scriptStream: InputStream?
    get() = getTextFileStream(R.color.grey_900, R.color.grey_600, false)

  private val pdfStream: InputStream?
    get() {
      val renderer = PdfRenderer(ParcelFileDescriptor.open(File(model.path),
        ParcelFileDescriptor.MODE_READ_ONLY))
      if (renderer.pageCount == 0) return null
      val page = renderer.openPage(0)
      val pageSize = Size(page.width, page.height)
      val scale = BitmapUtils.getCenterCropScaleFactorSize(Size(width, height), pageSize)
      val original = Bitmap.createBitmap(
        (page.width * scale).roundToInt(),
        (page.height * scale).roundToInt(),
        Bitmap.Config.ARGB_8888,
      )
      val transform = Matrix()
      transform.postScale(scale, scale)
      page.render(original, null, transform, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
      page.close()
      val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
      val canvas = Canvas(bitmap)
      canvas.drawColor(Color.WHITE)
      val translate = Matrix()
      translate.postTranslate(((width - original.width) / 2).toFloat(),
        ((height - original.height) / 2).toFloat())
      canvas.drawBitmap(original, translate, null)
      original.recycle()
      val stream = bitmapToStream(bitmap)
      bitmap.recycle()
      return stream
    }

  private val officeStream: InputStream?
    get() = null

  override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
    try {
      val inputSteam: InputStream? = when (extensionInfo.mimeType) {
        FileInfo.ExtensionInfo.GlideMimeType.IMAGE,
        FileInfo.ExtensionInfo.GlideMimeType.GIF,
        -> basicStream
        FileInfo.ExtensionInfo.GlideMimeType.VIDEO -> videoStream
        FileInfo.ExtensionInfo.GlideMimeType.TEXT -> textStream
        FileInfo.ExtensionInfo.GlideMimeType.AUDIO -> audioStream
        FileInfo.ExtensionInfo.GlideMimeType.DOCUMENT,
        FileInfo.ExtensionInfo.GlideMimeType.SPREADSHEET,
        FileInfo.ExtensionInfo.GlideMimeType.PRESENTATION,
        -> officeStream
        FileInfo.ExtensionInfo.GlideMimeType.PDF -> pdfStream
        FileInfo.ExtensionInfo.GlideMimeType.APK -> apkStream
        FileInfo.ExtensionInfo.GlideMimeType.CODE_SCRIPT -> scriptStream
        else -> null
      }
      Log.v("fixate", "loaded")
      callback.onDataReady(inputSteam)
    } catch (e: Exception) {
      Log.e("fixate", "Error loading preview for " + model.path, e)
      throw e
    }

  }

  override fun cleanup() {
    if (mInputStream != null) {
      try {
        mInputStream!!.close()
      } catch (e: IOException) {
        // Ignored
      } finally {
        mInputStream = null
      }
    }
  }

  override fun cancel() {
    if (mInputStream != null) {
      try {
        mInputStream!!.close()
      } catch (e: IOException) {
      } finally {
        mInputStream = null
      }
    }
  }

  private fun bitmapToStream(bitmap: Bitmap): ByteArrayInputStream {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return ByteArrayInputStream(stream.toByteArray())
  }

  private fun getTextFileStream(
    backgroundColor: Int,
    foregroundColor: Int,
    lineBreak: Boolean = true,
  ): InputStream? {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    val canvas = Canvas(bitmap)
    val textPaint = TextPaint()
    textPaint.color = ResourcesCompat.getColor(context.resources, foregroundColor, context.theme)
    textPaint.textSize = height / 9f
    canvas.drawColor(ResourcesCompat.getColor(context.resources, backgroundColor, context.theme))
    val text = TextFileUtils.readFile(model.file);
    return if (text.isEmpty() || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      bitmap.recycle()
      null
    } else {
      val textLayout = StaticLayout.Builder.obtain(text,
        0,
        text.length,
        textPaint,
        if (lineBreak) width - 64 else Int.MAX_VALUE).apply {
        if (lineBreak) {
          @SuppressLint("WrongConstant") if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setBreakStrategy(LineBreaker.BREAK_STRATEGY_SIMPLE)
          } else {
            setBreakStrategy(StaticLayout.BREAK_STRATEGY_SIMPLE)
          }
        }
        setLineSpacing(4f, 1f)
        setMaxLines(ceil(height / textPaint.textSize.toDouble()).toInt())
      }
      canvas.translate(32f, 24f)
      textLayout.build().draw(canvas)
      bitmapToStream(bitmap)
    }
  }

}
