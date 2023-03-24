allprojects {
  repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
}

group = "dev.jatzuk"
version = "1.0.0"

plugins {
  kotlin("multiplatform") apply false
  id("org.jetbrains.compose") apply false
}
