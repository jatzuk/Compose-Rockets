package scene

import geometry.Vector2

class Rocket {

    var position = Vector2(1f, -2f).normalized()

    var velocity = Vector2(0f, -1f)

    fun setDirection(vector2: Vector2) {
//        position = vector2.normalized()
    }

    fun getDirection() = (position ).radians()

    fun fly() {
        position += velocity
    }

    fun reset(x: Float, y: Float) {
        position = Vector2(x - (WIDTH / 2), y - HEIGHT)
    }

    companion object {

        const val WIDTH = 25f
        const val HEIGHT = 100f
    }
}
