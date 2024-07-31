package dev.jatzuk.rockets.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.jatzuk.rockets.common.scene.Scene
import dev.jatzuk.rockets.common.scene.SceneDimensions

@Composable
fun App() = MaterialTheme {
  var size by remember { mutableStateOf(IntSize.Zero) }

  Box(
    modifier = Modifier
      .safeDrawingPadding()
      .fillMaxSize()
      .onGloballyPositioned {
        size = it.size
      }
  ) {
    if (size == IntSize.Zero) {
      CircularProgressIndicator(
        modifier = Modifier
          .size(64.dp)
          .align(Alignment.Center)
      )
    } else {
      val sceneDimensions = SceneDimensions(size.width.toFloat(), size.height.toFloat())
      val scene = Scene(sceneDimensions)
      scene.setup()
      Renderer(scene)
    }
  }
}
