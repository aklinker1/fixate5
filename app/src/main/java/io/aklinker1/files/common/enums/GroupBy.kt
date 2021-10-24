package io.aklinker1.files.common.enums

enum class GroupBy : SerializableEnum<GroupBy> {
  NONE, TYPE;

  override fun from(name: String): GroupBy = valueOf(name)
  override fun write(): String = name
}
