package dev.jatzuk.rockets.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.jatzuk.rockets.common.App

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    title = "Compose Rockets",
    resizable = false
  ) {
    App()
  }
}
