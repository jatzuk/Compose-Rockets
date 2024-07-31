package dev.jatzuk.rockets.desktop

import androidx.compose.material.MaterialTheme
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
    val toolkit = Toolkit.getDefaultToolkit()
    with(toolkit.screenSize) {
      val sceneDimensions = SceneDimensions(width.toFloat(), height.toFloat())

      scene = Scene(sceneDimensions)
      scene.setup()
    }

    MaterialTheme {
      Renderer(scene)
    }
  }
}
