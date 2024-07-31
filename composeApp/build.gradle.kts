import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.util.Properties

plugins {
  alias(libs.plugins.multiplatform)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.compose)
  alias(libs.plugins.android.application)
}

kotlin {
  androidTarget {
    compilations.all {
      compileTaskProvider {
        compilerOptions {
          jvmTarget.set(JvmTarget.JVM_17)
          //https://jakewharton.com/gradle-toolchains-are-rarely-a-good-idea/#what-do-i-do
          freeCompilerArgs.add("-Xjdk-release=${JavaVersion.VERSION_17}")
        }
      }
    }

    //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
  }

  @OptIn(ExperimentalWasmDsl::class)
  wasmJs {
    moduleName = "composeApp"
    browser {
      val projectDirPath = project.projectDir.path
      commonWebpackConfig {
        outputFileName = "composeApp.js"
        devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
          static = (static ?: mutableListOf()).apply {
            // Serve sources to debug inside browser
            add(projectDirPath)
          }
        }
      }
    }
    binaries.executable()
  }

  jvm()

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "ComposeApp"
      isStatic = true
    }
  }

  sourceSets {
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
    }

    jvmMain.dependencies {
      implementation(compose.desktop.currentOs)
    }

    androidMain.dependencies {
//      implementation(compose.uiTooling)
      implementation(libs.androidx.activityCompose)
    }

    iosMain.dependencies {
    }
  }
}

android {
  namespace = "dev.jatzuk.composerockets"
  compileSdk = 34

  defaultConfig {
    minSdk = 26
    targetSdk = 34

    applicationId = "dev.jatzuk.composerockets.androidApp"
    versionCode = 1
    versionName = "1.0.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  signingConfigs {
    maybeCreate("release").apply {
      val properties = Properties()
      val file = File(project.rootDir, "local.properties")
      file.inputStream().use { properties.load(it) }

      keyAlias = properties.getProperty("keyAlias")
      keyPassword = properties.getProperty("keyPassword")
      storeFile = file("${project.rootDir.path}/${properties.getProperty("storeFile")}")
      storePassword = properties.getProperty("storePassword")
    }
  }

//  //https://developer.android.com/studio/test/gradle-managed-devices
//  @Suppress("UnstableApiUsage")
//  testOptions {
//    managedDevices.devices {
//      maybeCreate<ManagedVirtualDevice>("pixel5").apply {
//        device = "Pixel 5"
//        apiLevel = 34
//        systemImageSource = "aosp"
//      }
//    }
//  }

  buildFeatures {
    compose = true
  }

  composeOptions {

  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }

  buildTypes {
    debug {
      isMinifyEnabled = false
      isDebuggable = true
    }

    release {
      isMinifyEnabled = false
      isDebuggable = false

      signingConfig = signingConfigs.getByName("release")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "dev.jatzuk.composerockets"
      packageVersion = "1.0.0"
    }
  }
}
