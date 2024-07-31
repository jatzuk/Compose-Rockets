package dev.jatzuk.rockets.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composerockets.composeapp.generated.resources.Res
import composerockets.composeapp.generated.resources.rocket
import composerockets.composeapp.generated.resources.target
import dev.jatzuk.rockets.common.math.toDegrees
import dev.jatzuk.rockets.common.models.Rocket
import dev.jatzuk.rockets.common.models.Target
import dev.jatzuk.rockets.common.models.barriers.Barrier
import dev.jatzuk.rockets.common.models.barriers.BlockBarrier
import dev.jatzuk.rockets.common.models.barriers.TextBarrier
import dev.jatzuk.rockets.common.scene.Scene
import dev.jatzuk.rockets.common.scene.Stats
import org.jetbrains.compose.resources.imageResource

@Composable
fun Renderer(scene: Scene) {
  val rocketImage = imageResource(Res.drawable.rocket)
  val targetImage = imageResource(Res.drawable.target)

  val ticks = scene.ticks.collectAsState()
  Canvas(
    modifier = Modifier
      .background(Color.Black)
      .fillMaxSize()
  ) {
    drawTarget(scene.target, targetImage)

    scene.population.rockets.forEach { rocket ->
      drawRocket(rocket, rocketImage, ticks.value)
    }

    scene.barriers.forEach { barrier ->
      drawBarrier(barrier)
    }
  }

  DrawStats(scene.stats)
}

private fun DrawScope.drawTarget(target: Target, image: ImageBitmap) {
  translate(target.position.x, target.position.y) {
    drawImage(
      image = image,
      dstSize = IntSize(target.radius, target.radius),
      dstOffset = IntOffset(-target.radius / 2, -target.radius / 2),
    )

    drawRect(
      color = Color.Red,
      topLeft = Offset(-target.radius / 2f, -target.radius / 2f),
      size = Size(target.radius.toFloat(), target.radius.toFloat()),
      style = Stroke(2f)
    )
  }
}

private fun DrawScope.drawRocket(rocket: Rocket, image: ImageBitmap, ticks: Int) {
  if (rocket.isAlive && ticks > -1) {
    translate(rocket.position.x, rocket.position.y) {
      rotate(
        degrees = rocket.velocity.heading().toDegrees().toFloat(),
        pivot = Offset.Zero
      ) {
        drawImage(
          image = image,
          dstSize = IntSize(rocket.width, rocket.height),
          dstOffset = IntOffset(-rocket.width / 2, -rocket.height / 2),
        )

        drawRect(
          color = Color.Red,
          topLeft = Offset(-rocket.width / 2f, -rocket.height / 2f),
          size = Size(rocket.width.toFloat(), rocket.height.toFloat()),
          style = Stroke(2f)
        )
      }
    }
  }

  drawRocketPath(rocket)
}

private fun DrawScope.drawRocketPath(rocket: Rocket) {
  val color = when {
    !rocket.isAlive -> Color.Red
    rocket.isTargetReached -> Color.Green
    else -> Color.Yellow
  }
  rocket.path.forEach { position ->
    drawCircle(
      center = Offset(position.x, position.y),
      color = color,
      alpha = 0.35f,
      radius = 3f
    )
  }
}

private fun DrawScope.drawBarrier(barrier: Barrier) {
  translate(barrier.position.x, barrier.position.y) {
    when (barrier) {
      is BlockBarrier -> drawBlockBarrier(barrier)
      is TextBarrier -> drawTextBarrier(barrier)
    }
  }
}

private fun DrawScope.drawTextBarrier(barrier: TextBarrier) {
  drawRect(
    color = Color.Red,
    size = Size(barrier.width.toFloat(), barrier.height.toFloat())
  )

  drawText(textLayoutResult = barrier.text)
}

private fun DrawScope.drawBlockBarrier(barrier: BlockBarrier) {
  drawRect(
    color = barrier.color,
    style = Stroke(width = 1f),
    size = Size(barrier.width.toFloat(), barrier.height.toFloat())
  )
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
    DisplayGeneration(stats.generationCount)
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
private fun DisplayGeneration(count: Int) {
  DrawText("generation #$count")
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
