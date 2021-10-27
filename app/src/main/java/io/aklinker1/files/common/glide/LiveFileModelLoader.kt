package io.aklinker1.files.common.glide

import android.content.Context
import android.util.Log
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import io.aklinker1.livefs.LiveFile
import java.io.InputStream


class LiveFileModelLoader constructor(var context: Context) : ModelLoader<LiveFile, InputStream> {

  class Factory(var context: Context) : ModelLoaderFactory<LiveFile, InputStream> {

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<LiveFile, InputStream> {
      return LiveFileModelLoader(context)
    }

    override fun teardown() {}

  }

  override fun handles(fixateFile: LiveFile): Boolean {
    return true
  }

  override fun buildLoadData(
    model: LiveFile, width: Int, height: Int, options: Options
  ): ModelLoader.LoadData<InputStream>? {
    Log.d("fixate", "Loading preview for $model")
    return ModelLoader.LoadData(CacheKey(model), LiveFileDataFetcher(context, model, width, height))
  }

}
