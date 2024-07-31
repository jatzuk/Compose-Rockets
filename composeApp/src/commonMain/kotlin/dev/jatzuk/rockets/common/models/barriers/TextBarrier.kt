package dev.jatzuk.rockets.common.models.barriers

import androidx.compose.ui.text.TextLayoutResult
import dev.jatzuk.rockets.common.math.Vector2

class TextBarrier(
  val text: TextLayoutResult,
  override val position: Vector2
) : Barrier {

  override val width: Int = text.size.width
  override val height: Int = text.size.height
}
