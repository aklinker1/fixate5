package io.aklinker1.files.common.utils.fs

import android.content.res.Resources
import android.os.Environment
import androidx.annotation.DrawableRes
import io.aklinker1.files.R
import io.aklinker1.livefs.LiveFile

object KnownFilePaths {
  val HOME = Environment.getExternalStorageDirectory()?.absolutePath
  val TRASH = "${Environment.getDataDirectory()}/.trash"

  val ALARMS =
    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)?.absolutePath
  val DCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)?.absolutePath
  val DOCUMENTS =
    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)?.absolutePath
  val DOWNLOADS =
    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)?.absolutePath
  val MOVIES =
    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)?.absolutePath
  val MUSIC =
    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)?.absolutePath
  val NOTIFICATIONS =
    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS)?.absolutePath
  val PICTURES =
    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)?.absolutePath
  val PODCASTS =
    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS)?.absolutePath
  val RINGTONES =
    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES)?.absolutePath

  val ARCHIVE = if (HOME != null) "$HOME/Archive" else null
  val CARDBOARD = if (HOME != null) "$HOME/Cardboard" else null

}

val KnownFileIcons = mapOf<String?, @DrawableRes Int>(
  KnownFilePaths.HOME to R.drawable.folder_home,
  KnownFilePaths.TRASH to R.drawable.folder_trash,
  KnownFilePaths.ALARMS to R.drawable.folder_alarms,
  KnownFilePaths.DCIM to R.drawable.folder_dcim,
  KnownFilePaths.DOCUMENTS to R.drawable.folder_documents,
  KnownFilePaths.DOWNLOADS to R.drawable.folder_downloads,
  KnownFilePaths.MOVIES to R.drawable.folder_movies,
  KnownFilePaths.MUSIC to R.drawable.folder_music,
  KnownFilePaths.NOTIFICATIONS to R.drawable.folder_notifications,
  KnownFilePaths.PICTURES to R.drawable.folder_pictures,
  KnownFilePaths.PODCASTS to R.drawable.folder_podcasts,
  KnownFilePaths.RINGTONES to R.drawable.folder_ringtones,
  KnownFilePaths.ARCHIVE to R.drawable.folder_archive,
  KnownFilePaths.CARDBOARD to R.drawable.folder_cardboard,
)

val KnownFileAliases =
  mapOf<String?, @DrawableRes Int>(KnownFilePaths.HOME to R.string.folder_alias_home,
    KnownFilePaths.TRASH to R.string.folder_alias_trash)

fun LiveFile.nameOrAlias(resources: Resources): String {
  val alias = KnownFileAliases[this.path]
  return if (alias == null) this.name() else resources.getString(alias)
}
