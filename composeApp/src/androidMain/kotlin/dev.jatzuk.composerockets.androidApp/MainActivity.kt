package dev.jatzuk.composerockets.androidApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import dev.jatzuk.rockets.common.Renderer
import dev.jatzuk.rockets.common.scene.Scene
import dev.jatzuk.rockets.common.scene.SceneDimensions

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MaterialTheme {
        Box(
          modifier = Modifier
            .safeDrawingPadding()
            .fillMaxSize()
        ) {
          val displayMetrics = LocalContext.current.resources.displayMetrics
          val (width, height) = with(displayMetrics) {
            widthPixels to heightPixels
          }

          val sceneDimensions = SceneDimensions(width.toFloat(), height.toFloat())

          val scene = Scene(sceneDimensions)
          scene.setup()
          Renderer(scene)
        }
      }
    }
  }
}
