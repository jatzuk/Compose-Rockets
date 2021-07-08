import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import scene.Rocket
import scene.Scene
import utils.middleX

private const val TARGET_RADIUS = 50f

@Composable
fun Renderer(scene: Scene) {
    DrawTarget()
    DrawGameTick(scene.ticks.collectAsState().value)
    DrawRocket(scene.rocket)
}

@Composable
private fun DrawRocket(rocket: Rocket) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        println("rocket digrees: ${rocket.getDirection()}")
        rotate(
            degrees = rocket.getDirection().toFloat(),
        ) {
            drawRect(
                color = Color.Yellow,
                topLeft = Offset(rocket.position.x, rocket.position.y),
                size = Size(Rocket.WIDTH, Rocket.HEIGHT)
            )
            drawCircle(
                color = Color.Cyan,
                center = Offset(rocket.position.x + (Rocket.WIDTH / 2), rocket.position.y),
                radius = Rocket.WIDTH
            )
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
private fun DrawGameTick(gameTick: Int) {
    Text(
        modifier = Modifier
            .offset(10.dp, 10.dp),
        text = "tick #$gameTick",
        style = TextStyle(
            color = Color.White,
            fontSize = 24.sp
        )
    )
}
