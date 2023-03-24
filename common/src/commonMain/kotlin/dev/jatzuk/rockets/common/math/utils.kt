package dev.jatzuk.rockets.common.math

import kotlin.math.sqrt
import kotlin.random.Random

fun Float.toDegrees() = this / Math.PI * 180 + 90

fun randomVector(from: Double, to: Double) =
  Vector2(Random.nextDouble(from, to).toFloat(), Random.nextDouble(from, to).toFloat())

fun distance(from: Vector2, to: Vector2): Float {
  val a = (from.x - to.x) * (from.x - to.x)
  val b = (from.y - to.y) * (from.y - to.y)
  return sqrt(a + b)
}

fun map(n: Int, from: IntRange, to: IntRange) =
  (n - from.first) * (to.last - to.first) / (from.last - from.first) + to.first
