package dev.jatzuk.rockets.common.math

import dev.jatzuk.rockets.common.models.Rocket
import dev.jatzuk.rockets.common.models.barriers.Barrier

fun rocketBarrier(rocket: Rocket, barrier: Barrier): Boolean {
  val rocketLeft = rocket.position.x - rocket.width / 2
  val rocketRight = rocket.position.x + rocket.width / 2
  val rocketTop = rocket.position.y - rocket.height / 2
  val rocketBottom = rocket.position.y + rocket.height / 2

  val barrierLeft = barrier.position.x - barrier.width / 2
  val barrierRight = barrier.position.x + barrier.width / 2
  val barrierTop = barrier.position.y - barrier.height / 2
  val barrierBottom = barrier.position.y + barrier.height / 2

  return rocketLeft < barrierRight && rocketRight > barrierLeft && rocketTop < barrierBottom && rocketBottom > barrierTop
}
