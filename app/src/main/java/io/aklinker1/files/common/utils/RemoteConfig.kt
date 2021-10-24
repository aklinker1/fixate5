package io.aklinker1.files.common.utils

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfig {

  fun init() {
    val remoteConfig = Firebase.remoteConfig
    val configSettings = remoteConfigSettings {
      minimumFetchIntervalInSeconds = 3600
    }
    remoteConfig.setConfigSettingsAsync(configSettings)
  }

  // val isMultiselectDisabled: Boolean
  //   get() = Firebase.remoteConfig.getBoolean("disable_multiselect")
}
