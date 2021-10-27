package io.aklinker1.files.common.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import io.aklinker1.livefs.LiveFile
import java.io.InputStream

@GlideModule
@Suppress("unused")
class GlideConfig : AppGlideModule() {

  override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    registry.prepend(LiveFile::class.java,
      InputStream::class.java,
      LiveFileModelLoader.Factory(context))
  }

}
