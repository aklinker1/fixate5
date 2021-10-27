package io.aklinker1.files.common.utils

import java.util.concurrent.ThreadLocalRandom

object MathUtils {

  fun randomInt(min: Int = 0, max: Int = 10): Int {
    return if (max <= min)
      min
    else
      ThreadLocalRandom.current().nextInt(min, max + 1)
  }

  fun randomLong(min: Long = 0, max: Long = 10): Long {
    return if (max <= min)
      min
    else
      ThreadLocalRandom.current().nextLong(min, max + 1)
  }

}
