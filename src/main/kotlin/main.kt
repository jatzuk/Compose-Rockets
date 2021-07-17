import androidx.compose.desktop.LocalAppWindow
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import scene.Scene
import ui.Renderer

fun main() = Window(
    "Compose Rockets"
) {
    val scene: Scene

    with(LocalDensity.current) {
        val width = LocalAppWindow.current.width * density
        val height = LocalAppWindow.current.height * density

        scene = Scene(width, height)
        scene.setup()
    }

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black)
        ) {
            Renderer(scene)
        }
    }
}
