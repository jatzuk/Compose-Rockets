package dev.jatzuk.rockets.common.models

import dev.jatzuk.rockets.common.math.Vector2
import dev.jatzuk.rockets.common.math.distance
import dev.jatzuk.rockets.common.math.map
import dev.jatzuk.rockets.common.scene.Scene

class Rocket(
  val target: Target,
  val dna: DNA = DNA(Scene.GAME_TICKS_LIMIT),
  private val sceneHeight: Int
) {

  var position = Vector2()
    private set

  var velocity = Vector2()
    private set

  private var acceleration = Vector2()

  private val _path = mutableListOf<Vector2>()
  val path get() = _path.toList()

  var isAlive = true
  var isTargetReached = false

  var fitness = 0
    private set

  fun fly(tick: Int) {
    if (isTargetReached || !isAlive) return

    applyForce(dna.genes[tick])
    velocity += acceleration
    position += velocity
    acceleration *= 0
    velocity.limit(5)

    _path.add(position)

    if (distance(position, target.position) <= target.radius) isTargetReached = true
  }

  fun calculateFitness() {
    fitness = map(distance(position, target.position).toInt(), sceneHeight..0, 0..100)
    if (isTargetReached) fitness += 10
    if (!isAlive) fitness = 0
    fitness = fitness.coerceIn(0..100)
  }

  private fun applyForce(force: Vector2) {
    acceleration += force
  }

  fun reset(x: Float, y: Float) {
    position = Vector2(x - (WIDTH / 2), y - HEIGHT)
    velocity *= 0
    isAlive = true
    isTargetReached = false
    _path.clear()
  }

  fun death() {
    isAlive = false
  }

  companion object {

    const val WIDTH = 40f
    const val HEIGHT = WIDTH * 2
  }
}
