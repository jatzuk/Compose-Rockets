import kotlin.math.abs

data class Vector2(var x: Int, var y: Int) {

    operator fun plus(vector2: Vector2) = Vector2(x + vector2.x, y + vector2.y)

    fun normalize() = Vector2(abs(x / x), abs(y / y))
}