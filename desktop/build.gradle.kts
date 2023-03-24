import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.js.translate.context.Namer.kotlin

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
}

group = "dev.jatzuk.rockets"
version = "1.0-SNAPSHOT"

kotlin {
  jvm {
    jvmToolchain(11)
    withJava()
  }
  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation(project(":common"))
        implementation(compose.desktop.currentOs)
      }
    }
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"
    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "ComposeRockets"
      packageVersion = "1.0.0"
    }
  }
}
