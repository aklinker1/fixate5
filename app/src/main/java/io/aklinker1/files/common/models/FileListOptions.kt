package io.aklinker1.files.common.models

import io.aklinker1.files.common.enums.GroupBy
import io.aklinker1.files.common.enums.SortBy

data class FileListOptions(val groupBy: GroupBy, val sortBy: SortBy, val foldersFirst: Boolean)
