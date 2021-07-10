package models

import geometry.Vector2
import scene.Scene

class Rocket {

    var position = Vector2()
    var velocity = Vector2()
    var acceleration = Vector2()

    private val dna = DNA(Scene.GAME_TICKS_LIMIT)

    private var step = 0

    var isAlive = true

    fun fly() {
        if (isAlive) {
            applyForce(dna.genes[step++])
            velocity += acceleration
            position += velocity
            acceleration *= 0
            velocity.limit(4)
        }
    }

    private fun applyForce(force: Vector2) {
        acceleration += force
    }

    fun reset(x: Float, y: Float) {
        position = Vector2(x - (WIDTH / 2), y - HEIGHT)
        step = 0
        velocity *= 0
        isAlive = true
    }

    fun death() {
        isAlive = false
    }

    companion object {

        const val WIDTH = 20f
        const val HEIGHT = WIDTH * 5
    }
}
