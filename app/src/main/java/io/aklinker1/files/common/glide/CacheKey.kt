package io.aklinker1.files.common.glide

import com.bumptech.glide.load.Key
import io.aklinker1.livefs.LiveFile
import java.security.MessageDigest

class CacheKey(val file: LiveFile) : Key {

  override fun toString(): String {
    return file.toString()
  }

  override fun equals(other: Any?): Boolean {
    if (other is CacheKey) {
      return file.path + file.file.lastModified() == other.file.path + other.file.file.lastModified()
    }
    return false
  }

  override fun hashCode(): Int {
    return file.hashCode()
  }

  override fun updateDiskCacheKey(messageDigest: MessageDigest) {
    messageDigest.update(file.toString().toByteArray(Key.CHARSET))
  }
}
