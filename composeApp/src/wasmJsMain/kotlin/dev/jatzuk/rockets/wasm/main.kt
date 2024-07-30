package dev.jatzuk.rockets.wasm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.ComposeViewport
import dev.jatzuk.rockets.common.Renderer
import dev.jatzuk.rockets.common.scene.Scene
import dev.jatzuk.rockets.common.scene.SceneDimensions
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
  ComposeViewport(document.body!!) {
//    with(Toolkit.getDefaultToolkit().screenSize) {
    val width = 1728f//window.innerWidth.toFloat()
    val height = 1117f //window.innerHeight.toFloat()
    val sceneDimensions = SceneDimensions(width, height)

    val scene = Scene(sceneDimensions)
    scene.setup()

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
