package geometry

import kotlin.math.atan2
import kotlin.math.sqrt

data class Vector2(var x: Float, var y: Float) {

    operator fun plus(vector2: Vector2) = Vector2(x + vector2.x, y + vector2.y)

    fun normalized(): Vector2 {
        val nX = if (x != 0f) x / magnitude() else 1f
        val nY = if (y != 0f) y / magnitude() else 1f
        return Vector2(nX, nY)
    }

    fun magnitude() = sqrt((x * x).toDouble() + (y * y).toDouble()).toFloat()

    fun radians() = atan2(y, x) / Math.PI * 180
}
