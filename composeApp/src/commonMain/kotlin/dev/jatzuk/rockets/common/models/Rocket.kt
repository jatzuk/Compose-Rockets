package dev.jatzuk.rockets.common.models

import dev.jatzuk.rockets.common.math.Vector2
import dev.jatzuk.rockets.common.math.distance
import dev.jatzuk.rockets.common.math.map
import dev.jatzuk.rockets.common.scene.Scene

class Rocket(
  val target: Target,
  val dna: DNA = DNA(Scene.GAME_TICKS_LIMIT),
  private val sceneWidth: Int,
  private val sceneHeight: Int,
) {

  val width = SIZE
  val height = SIZE

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
    if (isTargetReached || !isAlive) {
      return
    }

    applyForce(dna.genes[tick])

    velocity += acceleration
    position += velocity
    acceleration *= 0
    velocity.limit(5)

    _path.add(position)

    if (distance(position, target.position) < target.radius) {
      isTargetReached = true
    }
  }

  fun isInsideScene(): Boolean {
    val halfWidth = width / 2
    val halfHeight = height / 2

    return position.x - halfWidth >= 0 &&
      position.x + halfWidth <= sceneWidth &&
      position.y - halfHeight >= 0 &&
      position.y + halfHeight <= sceneHeight
  }

  fun calculateFitness() {
    fitness = map(distance(position, target.position).toInt(), sceneHeight..0, 0..100)
    if (!isAlive) {
      fitness = 0
    }
    if (isTargetReached) {
      fitness += 15
    }
    fitness = fitness.coerceIn(0..100)
  }

  fun reset(x: Float, y: Float) {
    position = Vector2(x, y)
    velocity *= 0
    isAlive = true
    isTargetReached = false
    _path.clear()
  }

  fun death() {
    isAlive = false
  }

  private fun applyForce(force: Vector2) {
    acceleration += force
  }

  companion object {
    const val SIZE = 100
  }
}
