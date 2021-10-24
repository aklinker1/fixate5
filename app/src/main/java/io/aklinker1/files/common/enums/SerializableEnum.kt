package io.aklinker1.files.common.enums

interface SerializableEnum<T> {
  fun write(): String
  fun from(name: String): T
}
