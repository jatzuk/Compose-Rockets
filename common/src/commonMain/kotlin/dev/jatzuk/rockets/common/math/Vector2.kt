package dev.jatzuk.rockets.common.math

import kotlin.math.atan2
import kotlin.math.sqrt

data class Vector2(var x: Float, var y: Float) {

  constructor() : this(0f, 0f)

  operator fun plus(vector2: Vector2) = Vector2(x + vector2.x, y + vector2.y)

  operator fun minus(vector2: Vector2) = Vector2(x - vector2.x, y - vector2.y)

  operator fun times(n: Int) = Vector2(x * n, y * n)

  operator fun times(n: Float) = Vector2(x * n, y * n)

  operator fun div(n: Int) = Vector2(x / n, y / n)

  fun normalized(): Vector2 {
    val magnitude = magnitude()
    return if (magnitude != 0) Vector2(x / magnitude, y / magnitude) else this
  }

  fun magnitude() = sqrt((x * x) + (y * y)).toInt()

  fun setMagnitude(length: Float) {
    val vector2 = normalized() * length
    x = vector2.x
    y = vector2.y
  }

  /**
   * @return rotation in radians
   * */
  fun heading() = atan2(y, x)

  fun limit(n: Int) {
    val vector2 = limited(n)
    x = vector2.x
    y = vector2.y
  }

  fun limited(n: Int): Vector2 {
    val magnitude = magnitude()
    if (magnitude > n * n) {
      return Vector2(x, y) * n
    }
    return this
  }
}
