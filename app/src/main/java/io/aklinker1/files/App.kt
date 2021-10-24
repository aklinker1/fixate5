package io.aklinker1.files

import android.app.Application
import io.aklinker1.files.common.utils.RemoteConfig

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    RemoteConfig.init()
  }
}
