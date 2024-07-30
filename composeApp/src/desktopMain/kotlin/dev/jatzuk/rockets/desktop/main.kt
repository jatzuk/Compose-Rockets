package dev.jatzuk.rockets.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.jatzuk.rockets.common.Renderer
import dev.jatzuk.rockets.common.scene.Scene
import dev.jatzuk.rockets.common.scene.SceneDimensions
import java.awt.Toolkit

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    title = "Compose Rockets",
    resizable = false
  ) {
    val scene: Scene

    with(Toolkit.getDefaultToolkit().screenSize) {
      val sceneDimensions = SceneDimensions(width.toFloat(), height.toFloat())

      scene = Scene(sceneDimensions)
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
}
