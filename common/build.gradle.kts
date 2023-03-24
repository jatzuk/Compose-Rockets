plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
}

group = "dev.jatzuk.rockets"
version = "1.0-SNAPSHOT"

kotlin {
  jvm("desktop") {
    jvmToolchain(11)
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(compose.runtime)
        api(compose.foundation)
        api(compose.material)
      }
    }
  }
}
