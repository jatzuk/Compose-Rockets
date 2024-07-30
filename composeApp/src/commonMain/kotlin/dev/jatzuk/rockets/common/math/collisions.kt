package dev.jatzuk.rockets.common.math

import dev.jatzuk.rockets.common.models.Rocket
import dev.jatzuk.rockets.common.models.barriers.Barrier
import kotlin.math.sqrt

fun rocketBarrier(rocket: Rocket, blockBarrier: Barrier): Boolean {
  val xCheck = when {
    rocket.position.x < blockBarrier.position.x -> blockBarrier.position.x
    rocket.position.x > blockBarrier.position.x + blockBarrier.width -> blockBarrier.position.x + blockBarrier.width
    else -> rocket.position.x
  }

  val yCheck = when {
    rocket.position.y < blockBarrier.position.y -> blockBarrier.position.y
    rocket.position.y > blockBarrier.position.y + blockBarrier.height -> blockBarrier.position.y + blockBarrier.height
    else -> rocket.position.y
  }

  val xDistance = rocket.position.x - xCheck
  val yDistance = rocket.position.y - yCheck
  val distance = sqrt((xDistance * xDistance) + (yDistance * yDistance))
  return distance <= Rocket.WIDTH / 2
}
