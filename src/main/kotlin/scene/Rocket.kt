package scene

import geometry.Vector2
import kotlin.random.Random

class Rocket {

    var position = Vector2(0f, 0f)
    var velocity = Vector2(Random.nextDouble(-1.0, 1.0).toFloat(), -Random.nextDouble(-1.0, 1.0).toFloat())
    var acceleration = Vector2(Random.nextDouble(-1.0, 1.0).toFloat(), -Random.nextDouble(-1.0, 1.0).toFloat())

    fun fly() {
        val velocity = velocity + acceleration
        position += velocity
        acceleration *= 0
    }

    fun reset(x: Float, y: Float) {
        position = Vector2(x - (WIDTH / 2), y - HEIGHT)
    }

    companion object {

        const val WIDTH = 20f
        const val HEIGHT = WIDTH * 5
    }
}
