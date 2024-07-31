package dev.jatzuk.rockets.iosApp

import androidx.compose.ui.window.ComposeUIViewController
import dev.jatzuk.rockets.common.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
  App()
}
