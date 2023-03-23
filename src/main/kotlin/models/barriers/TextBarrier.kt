package models.barriers

import math.Vector2
import org.jetbrains.skija.Font
import org.jetbrains.skija.TextLine
import org.jetbrains.skija.Typeface

class TextBarrier(
  text: String,
  size: Float,
  override val position: Vector2
) : Barrier {

  val textLine = TextLine.make(text, Font(Typeface.makeDefault(), size))

  override val width: Int = textLine.width.toInt()
  override val height: Int = textLine.height.toInt()
}
