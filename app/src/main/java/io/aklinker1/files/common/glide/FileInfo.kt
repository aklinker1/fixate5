package io.aklinker1.files.common.glide


import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import io.aklinker1.files.R
import io.aklinker1.files.common.utils.ResourceUtils
import io.aklinker1.files.common.utils.fs.MimeTypeMapPlus

object FileInfo {

  val extensionInfos = HashMap<String, ExtensionInfo>()
  val mimeTypes = MimeTypeMapPlus()

  fun defaultExtensionInfo(context: Context) =
    ExtensionInfo(null, R.drawable.file_unknown, false, ExtensionInfo.GlideMimeType.UNKNOWN)

  init {
    // Extension -> Color & Icon
    extensionInfos["png"] =
      ExtensionInfo(R.color.purple_500, R.drawable.file_image, true, ExtensionInfo.GlideMimeType.IMAGE)
    extensionInfos["jpg"] =
      ExtensionInfo(R.color.purple_500, R.drawable.file_image, true, ExtensionInfo.GlideMimeType.IMAGE)
    extensionInfos["jpeg"] =
      ExtensionInfo(R.color.purple_500, R.drawable.file_image, true, ExtensionInfo.GlideMimeType.IMAGE)
    extensionInfos["webp"] =
      ExtensionInfo(R.color.purple_500, R.drawable.file_image, true, ExtensionInfo.GlideMimeType.IMAGE)
    extensionInfos["gif"] =
      ExtensionInfo(R.color.dark_purple_500, R.drawable.file_anim, true, ExtensionInfo.GlideMimeType.GIF)
    extensionInfos["mp4"] =
      ExtensionInfo(R.color.deep_orange_500, R.drawable.file_video, true, ExtensionInfo.GlideMimeType.VIDEO)
    extensionInfos["mov"] =
      ExtensionInfo(R.color.deep_orange_500, R.drawable.file_video, true, ExtensionInfo.GlideMimeType.VIDEO)
    extensionInfos["webm"] =
      ExtensionInfo(R.color.deep_orange_500, R.drawable.file_video, true, ExtensionInfo.GlideMimeType.VIDEO)
    extensionInfos["mp3"] =
      ExtensionInfo(R.color.orange_500, R.drawable.file_audio, true, ExtensionInfo.GlideMimeType.AUDIO)
    extensionInfos["wav"] =
      ExtensionInfo(R.color.orange_500, R.drawable.file_audio, true, ExtensionInfo.GlideMimeType.AUDIO)
    extensionInfos["txt"] =
      ExtensionInfo(R.color.green_500, R.drawable.file_text, false, ExtensionInfo.GlideMimeType.TEXT)
    extensionInfos["md"] =
      ExtensionInfo(R.color.grey_900, R.drawable.file_markdown, false, ExtensionInfo.GlideMimeType.CODE_SCRIPT)
    extensionInfos["xml"] = ExtensionInfo(
      R.color.light_blue_500,
      R.drawable.file_code_layout,
      false,
      ExtensionInfo.GlideMimeType.CODE_LAYOUT
    )
    extensionInfos["html"] = ExtensionInfo(
      R.color.light_blue_500,
      R.drawable.file_code_layout,
      false,
      ExtensionInfo.GlideMimeType.CODE_LAYOUT
    )
    extensionInfos["java"] = ExtensionInfo(
      R.color.teal_500,
      R.drawable.file_code_language,
      false,
      ExtensionInfo.GlideMimeType.CODE_SCRIPT
    )
    extensionInfos["css"] = ExtensionInfo(
      R.color.teal_500,
      R.drawable.file_code_language,
      false,
      ExtensionInfo.GlideMimeType.CODE_SCRIPT
    )
    extensionInfos["json"] = ExtensionInfo(
      R.color.teal_500,
      R.drawable.file_code_language,
      false,
      ExtensionInfo.GlideMimeType.CODE_SCRIPT
    )
    extensionInfos["cpp"] = ExtensionInfo(
      R.color.teal_500,
      R.drawable.file_code_language,
      false,
      ExtensionInfo.GlideMimeType.CODE_SCRIPT
    )
    extensionInfos["c"] = ExtensionInfo(
      R.color.teal_500,
      R.drawable.file_code_language,
      false,
      ExtensionInfo.GlideMimeType.CODE_SCRIPT
    )
    extensionInfos["hpp"] = ExtensionInfo(
      R.color.teal_500,
      R.drawable.file_code_language,
      false,
      ExtensionInfo.GlideMimeType.CODE_SCRIPT
    )
    extensionInfos["h"] = ExtensionInfo(
      R.color.teal_500,
      R.drawable.file_code_language,
      false,
      ExtensionInfo.GlideMimeType.CODE_SCRIPT
    )
    extensionInfos["go"] = ExtensionInfo(
      R.color.teal_500,
      R.drawable.file_code_language,
      false,
      ExtensionInfo.GlideMimeType.CODE_SCRIPT
    )
    extensionInfos["bat"] =
      ExtensionInfo(R.color.grey_900, R.drawable.file_run, false, ExtensionInfo.GlideMimeType.EXECUTABLE)
    extensionInfos["sh"] =
      ExtensionInfo(R.color.grey_900, R.drawable.file_run, false, ExtensionInfo.GlideMimeType.EXECUTABLE)
    extensionInfos["doc"] =
      ExtensionInfo(R.color.blue_500, R.drawable.file_document, true, ExtensionInfo.GlideMimeType.DOCUMENT)
    extensionInfos["docx"] =
      ExtensionInfo(R.color.blue_500, R.drawable.file_document, true, ExtensionInfo.GlideMimeType.DOCUMENT)
    extensionInfos["xls"] =
      ExtensionInfo(R.color.green_500, R.drawable.file_spreadsheet, true, ExtensionInfo.GlideMimeType.SPREADSHEET)
    extensionInfos["xlsx"] =
      ExtensionInfo(R.color.green_500, R.drawable.file_spreadsheet, true, ExtensionInfo.GlideMimeType.SPREADSHEET)
    extensionInfos["ppt"] =
      ExtensionInfo(R.color.red_500, R.drawable.file_presentation, true, ExtensionInfo.GlideMimeType.PRESENTATION)
    extensionInfos["pptx"] =
      ExtensionInfo(R.color.red_500, R.drawable.file_presentation, true, ExtensionInfo.GlideMimeType.PRESENTATION)
    extensionInfos["pdf"] =
      ExtensionInfo(R.color.red_500, R.drawable.file_pdf, true, ExtensionInfo.GlideMimeType.PDF)
    extensionInfos["zip"] =
      ExtensionInfo(R.color.amber_500, R.drawable.file_compressed, false, ExtensionInfo.GlideMimeType.ARCHIVE)
    extensionInfos["rar"] =
      ExtensionInfo(R.color.amber_500, R.drawable.file_compressed, false, ExtensionInfo.GlideMimeType.ARCHIVE)
    extensionInfos["7zip"] =
      ExtensionInfo(R.color.amber_500, R.drawable.file_compressed, false, ExtensionInfo.GlideMimeType.ARCHIVE)
    extensionInfos["apk"] =
      ExtensionInfo(R.color.light_green_500, R.drawable.file_app, true, ExtensionInfo.GlideMimeType.APK)
    extensionInfos["exe"] = ExtensionInfo(
      R.color.blue_grey_500,
      R.drawable.file_program,
      false,
      ExtensionInfo.GlideMimeType.UNKNOWN
    ) // TODO - BINARY Previews?
  }

  class ExtensionInfo(
    @ColorRes val color: Int?,
    @DrawableRes val typeIcon: Int,
    val hasError: Boolean,
    val mimeType: GlideMimeType
  ) {
    enum class GlideMimeType {
      UNKNOWN,
      IMAGE,
      VIDEO,
      AUDIO,
      TEXT,
      CODE,
      CODE_LAYOUT,
      CODE_SCRIPT,
      GIF,
      PDF,
      APK,
      DOCUMENT,
      SPREADSHEET,
      PRESENTATION,
      ARCHIVE,
      EXECUTABLE;
    }
  }

}
