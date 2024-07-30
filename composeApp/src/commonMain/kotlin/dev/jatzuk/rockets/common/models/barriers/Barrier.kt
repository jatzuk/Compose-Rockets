package dev.jatzuk.rockets.common.models.barriers

import dev.jatzuk.rockets.common.math.Vector2

interface Barrier {

  val position: Vector2
  val width: Int
  val height: Int
}
