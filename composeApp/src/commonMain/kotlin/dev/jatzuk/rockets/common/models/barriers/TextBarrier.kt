package dev.jatzuk.rockets.common.models.barriers

import dev.jatzuk.rockets.common.math.Vector2
import org.jetbrains.skia.Font
import org.jetbrains.skia.TextLine
import org.jetbrains.skia.Typeface

class TextBarrier(
  text: String,
  size: Float,
  override val position: Vector2
) : Barrier {

  val textLine = TextLine.make(text, Font(Typeface.makeDefault(), size))

  override val width: Int = textLine.width.toInt()
  override val height: Int = textLine.height.toInt()
}
