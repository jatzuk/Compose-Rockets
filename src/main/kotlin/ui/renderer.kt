package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import math.Vector2
import math.toDegrees
import models.Barrier
import models.Rocket
import models.Target
import org.jetbrains.skija.IRect
import org.jetbrains.skija.Image
import scene.Scene
import scene.Stats
import utils.ResourceLoader

@Composable
fun Renderer(scene: Scene) {
    val image = remember { ResourceLoader.getRocketImage() }
    val (width, height) = remember { Rocket.WIDTH to Rocket.HEIGHT }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawTarget(scene.target)
        scene.population.rockets.forEach { rocket -> drawRocket(rocket, image, width.toInt(), height.toInt()) }
        scene.barriers.forEach { barrier -> drawBarrier(barrier) }
    }
    DrawStats(scene.stats)
}

private fun DrawScope.drawRocket(rocket: Rocket, image: Image, width: Int, height: Int) {
    translate(rocket.position.x, rocket.position.y) {
        rotate(
            degrees = rocket.velocity.heading().toDegrees().toFloat(),
            pivot = Offset.Zero
        ) {
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawImageRect(
                    image,
                    IRect(-width, -width / 2, width, height).toRect()
                )
            }
        }
    }

    drawRocketPath(rocket.path)
}

private fun DrawScope.drawRocketPath(path: List<Vector2>) {
    path.forEach { position ->
        drawCircle(
            center = Offset(position.x, position.y),
            color = Color.Green,
            alpha = 0.35f,
            radius = 2f
        )
    }
}

private fun DrawScope.drawBarrier(barrier: Barrier) {
    translate(barrier.position.x, barrier.position.y) {
        drawRect(
            color = Color.Magenta,
            size = Size(80f, 40f)
        )
    }
}

private fun DrawScope.drawTarget(target: Target) {
    drawCircle(
        color = Color.Green,
        radius = target.radius,
        center = Offset(target.position.x, target.position.y)
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
        DisplayPopulation(stats.populationCount)
    }
}

@Composable
private fun DisplayGameTick(gameTick: Int) {
    DrawText("tick # $gameTick / ${Scene.GAME_TICKS_LIMIT}")
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
    DrawText("average fitness: $count")
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
    DrawText("population # $count")
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
