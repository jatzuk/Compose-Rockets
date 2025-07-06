package dev.jatzuk.rockets.common.models

import dev.jatzuk.rockets.common.math.Vector2
import kotlin.math.roundToInt

data class Target(
  val position: Vector2,
  val sceneWidth: Float,
  val sceneHeight: Float,
  val rocketSize: Int = Rocket.SIZE
) {

  val radius: Int get() = calculateDynamicRadius()
  
  val visualRadius: Int get() = (radius * 1.5f).roundToInt()

  private fun calculateDynamicRadius(): Int {
    return (rocketSize * 1.5f).roundToInt()
  }
}
