package models.barriers

import androidx.compose.ui.graphics.Color
import math.Vector2

class BlockBarrier(
  override val position: Vector2,
  override val width: Int,
  override val height: Int
) : Barrier {

  var color = Color.Yellow
}
