import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import math.toDegrees
import models.Barrier
import models.Rocket
import models.Target
import scene.Scene
import scene.Stats

@Composable
fun Renderer(scene: Scene) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawTarget(scene.target)
        scene.population.rockets.forEach { rocket -> drawRocket(rocket) }
        scene.barriers.forEach { barrier -> drawBarrier(barrier) }
    }
    DrawStats(scene.stats)
}

private fun DrawScope.drawRocket(rocket: Rocket) {
    translate(rocket.position.x, rocket.position.y) {
        rotate(
            degrees = rocket.velocity.heading().toDegrees().toFloat(),
            pivot = Offset.Zero
        ) {
            drawRect(
                color = if (rocket.isAlive) Color.Yellow else Color.Red,
                alpha = 0.75f,
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
