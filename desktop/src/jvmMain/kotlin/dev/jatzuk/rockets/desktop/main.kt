package dev.jatzuk.rockets.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.jatzuk.rockets.common.scene.Scene
import dev.jatzuk.rockets.common.scene.SceneDimensions

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    title = "Compose Rockets",
    resizable = false
  ) {
    val scene: Scene

    with(LocalDensity.current) {
      val width = window.width * density //LocalAppWindow.current.width * density
      val height = window.height * density
      val sceneDimensions = SceneDimensions(width, height)

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
