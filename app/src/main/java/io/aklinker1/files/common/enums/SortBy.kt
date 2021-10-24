package io.aklinker1.files.common.enums

import android.util.Log

enum class SortBy : SerializableEnum<SortBy> {
  FILENAME_ASC, FILENAME_DESC, LAST_MODIFIED_ASC, LAST_MODIFIED_DESC;

  override fun from(name: String): SortBy = valueOf(name)
  override fun write(): String = name
}
