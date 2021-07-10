package geometry

import kotlin.random.Random

fun Float.toDegrees() = this / Math.PI * 180 + 90

fun randomVector(from: Double, to: Double) =
    Vector2(Random.nextDouble(from, to).toFloat(), Random.nextDouble(from, to).toFloat())
