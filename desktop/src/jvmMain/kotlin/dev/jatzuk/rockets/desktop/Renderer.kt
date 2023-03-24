package dev.jatzuk.rockets.desktop

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jatzuk.rockets.common.math.toDegrees
import dev.jatzuk.rockets.common.models.Rocket
import dev.jatzuk.rockets.common.models.Target
import dev.jatzuk.rockets.common.models.barriers.Barrier
import dev.jatzuk.rockets.common.models.barriers.BlockBarrier
import dev.jatzuk.rockets.common.models.barriers.TextBarrier
import dev.jatzuk.rockets.common.scene.Scene
import dev.jatzuk.rockets.common.scene.Stats
import dev.jatzuk.rockets.common.utils.ResourceLoader
import org.jetbrains.skia.Image
import org.jetbrains.skia.Rect

@Composable
fun Renderer(scene: Scene) {
  val rocketImage = remember { ResourceLoader.getRocketImage() }
  val (rocketWidth, rocketHeight) = remember { Rocket.WIDTH to Rocket.HEIGHT }

  val targetImage = remember { ResourceLoader.getTargetImage() }

  val textBarrierPaint = remember {
    Paint().asFrameworkPaint().apply {
      isAntiAlias = true
      color = org.jetbrains.skia.Color.makeRGB(41, 132, 252)
    }
  }

  Canvas(modifier = Modifier.fillMaxSize()) {
    drawTarget(scene.target, targetImage)
    scene.population.rockets.forEach { rocket ->
      drawRocket(
        rocket,
        rocketImage,
        rocketWidth.toInt(),
        rocketHeight.toInt()
      )
    }
    scene.barriers.forEach { barrier -> drawBarrier(barrier, textBarrierPaint) }
  }
  DrawStats(scene.stats)
}

private fun DrawScope.drawRocket(rocket: Rocket, image: Image, width: Int, height: Int) {
  if (rocket.isAlive) {
    translate(rocket.position.x, rocket.position.y) {
      rotate(
        degrees = rocket.velocity.heading().toDegrees().toFloat(),
        pivot = Offset.Zero
      ) {
        drawIntoCanvas { canvas ->
          canvas.nativeCanvas.drawImageRect(
            image,
            Rect((-width).toFloat(), -width / 2f, width.toFloat(), height.toFloat())
//            org.jetbrains.skia.IRect().toRect()
          )
        }
      }
    }
  }

  drawRocketPath(rocket)
}

private fun DrawScope.drawRocketPath(rocket: Rocket) {
  val color = when {
    !rocket.isAlive -> Color.Red
    rocket.isTargetReached -> Color.Yellow
    else -> Color.Green
  }
  rocket.path.forEach { position ->
    drawCircle(
      center = Offset(position.x, position.y),
      color = color,
      alpha = 0.35f,
      radius = 2f
    )
  }
}

private fun DrawScope.drawBarrier(barrier: Barrier, textBarrierPaint: NativePaint) {
  translate(barrier.position.x, barrier.position.y) {
    when (barrier) {
      is BlockBarrier -> drawBlockBarrier(barrier)
      is TextBarrier -> drawTextBarrier(barrier, textBarrierPaint)
    }
  }
}

private fun DrawScope.drawTextBarrier(barrier: TextBarrier, paint: NativePaint) {
  drawIntoCanvas { canvas ->
    canvas.nativeCanvas.drawTextLine(
      barrier.textLine,
      0f,
      barrier.height.toFloat(),
      paint
    )
  }
}

private fun DrawScope.drawBlockBarrier(barrier: BlockBarrier) {
  drawRect(
    color = barrier.color,
    style = Stroke(width = 1f),
    size = Size(barrier.width.toFloat(), barrier.height.toFloat())
  )
}

private fun DrawScope.drawTarget(target: Target, image: Image) {
  translate(target.position.x - target.radius / 2, target.position.y - target.radius / 2) {
    drawIntoCanvas { canvas ->
      canvas.nativeCanvas.drawImageRect(
        image,
        Rect(
          -(target.radius),
          -(target.radius),
          target.radius * 2,
          target.radius * 2
        )
      )
    }
  }
}

@Composable
private fun DrawStats(stats: Stats) {
  Column(
    modifier = Modifier
      .fillMaxHeight()
      .padding(8.dp),
    verticalArrangement = Arrangement.Bottom
  ) {
    DisplayGameTick(stats.ticks.collectAsState().value)
    DisplayTargetReachedCount(stats.reachedTarget)
    DisplayBestFitness(stats.bestFitness)
    DisplayAverageFitness(stats.averageFitness)
    DisplayAliveCount(stats.aliveRockets)
    DisplayDeathCount(stats.deathRockets)
    DisplayPopulation(stats.populationCount)
  }
}

@Composable
private fun DisplayGameTick(gameTick: Int) {
  DrawText("tick: $gameTick / ${Scene.GAME_TICKS_LIMIT}")
}

@Composable
private fun DisplayTargetReachedCount(count: Int) {
  DrawText("target reached: $count")
}

@Composable
private fun DisplayBestFitness(count: Int) {
  DrawText("best fitness: $count")
}

@Composable
private fun DisplayAverageFitness(count: Int) {
  DrawText("avg fitness: $count")
}

@Composable
private fun DisplayAliveCount(count: Int) {
  DrawText(
    text = "alive: $count",
    textColor = Color.Green
  )
}

@Composable
private fun DisplayDeathCount(count: Int) {
  DrawText(
    text = "death: $count",
    textColor = Color.Red
  )
}

@Composable
private fun DisplayPopulation(count: Int) {
  DrawText("population #$count")
}

@Composable
private fun DrawText(text: String, textColor: Color = Color.White) {
  Text(
    text = text,
    style = TextStyle(
      color = textColor,
      fontSize = 18.sp
    )
  )
}
