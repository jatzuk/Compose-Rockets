import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import math.toDegrees
import models.Rocket
import scene.Scene
import scene.Stats
import utils.middleX

private const val TARGET_RADIUS = 50f

@Composable
fun Renderer(scene: Scene) {
    DrawTarget()
    scene.rockets.forEach { rocket -> DrawRocket(rocket) }
    DrawStats(scene.stats)
}

@Composable
private fun DrawRocket(rocket: Rocket) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(rocket.position.x, rocket.position.y) {
            rotate(
                degrees = rocket.velocity.heading().toDegrees().toFloat(),
                pivot = Offset.Zero
            ) {
                drawRect(
                    color = if (rocket.isAlive) Color.Yellow else Color.Red,
                    size = Size(Rocket.WIDTH, Rocket.HEIGHT)
                )
                drawCircle(
                    center = Offset(Rocket.WIDTH / 2f, 0f),
                    color = Color.Cyan,
                    radius = Rocket.WIDTH
                )
            }
        }
    }
}

@Composable
private fun DrawTarget() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = Color.Green,
            radius = TARGET_RADIUS,
            center = Offset(middleX(), 100f)
        )
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
        DisplayAliveCount(stats.aliveRockets.collectAsState().value)
        DisplayDeathCount(stats.deathRockets.collectAsState().value)
        DisplayPopulation(stats.populationCount.collectAsState().value)
    }
}

@Composable
private fun DisplayGameTick(gameTick: Int) {
    DrawText(
        text = "tick # $gameTick / ${Scene.GAME_TICKS_LIMIT}"
    )
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
    DrawText(
        text = "population # $count"
    )
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
