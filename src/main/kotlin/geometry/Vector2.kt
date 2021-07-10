package geometry

import kotlin.math.atan2
import kotlin.math.sqrt

data class Vector2(var x: Float, var y: Float) {

    operator fun plus(vector2: Vector2) = Vector2(x + vector2.x, y + vector2.y)

    operator fun minus(vector2: Vector2) = Vector2(x - vector2.x, y - vector2.y)

    operator fun times(n: Int) = Vector2(x * n, y * n)

    operator fun div(n: Int) = Vector2(x / n, y / n)

    fun normalized(): Vector2 {
        val m = magnitude()
        return if (m != 0) Vector2(x / m, y / m) else this
    }

    fun magnitude() = sqrt((x * x) + (y * y)).toInt()

    /**
     * @return rotation in radians
     * */
    fun heading() = atan2(y, x)
}
