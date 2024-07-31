package dev.jatzuk.rockets.wasm

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.jatzuk.rockets.common.App
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() = ComposeViewport(document.body!!) {
  App()
}

