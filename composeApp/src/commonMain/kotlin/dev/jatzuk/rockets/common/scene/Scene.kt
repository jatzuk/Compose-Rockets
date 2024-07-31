package dev.jatzuk.rockets.common.scene

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import dev.jatzuk.rockets.common.math.Vector2
import dev.jatzuk.rockets.common.math.rocketBarrier
import dev.jatzuk.rockets.common.models.Population
import dev.jatzuk.rockets.common.models.Target
import dev.jatzuk.rockets.common.models.barriers.Barrier
import dev.jatzuk.rockets.common.models.barriers.TextBarrier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class Scene(
  sceneDimensions: SceneDimensions,
) {

  private val sceneWidth = sceneDimensions.width
  private val sceneHeight = sceneDimensions.height

  private val sceneScope = CoroutineScope(Dispatchers.Default)
  private var sceneJob: Job? = null

  private val _ticks = MutableStateFlow(0)
  val ticks = _ticks.asStateFlow()

  var target = Target(Vector2(sceneWidth / 2f, getTargetYPosition()))
    private set

  val population = Population(ROCKETS_SIZE, target, sceneHeight.toInt())

  private val _barriers = mutableListOf<Barrier>()
  val barriers: List<Barrier> get() = _barriers

  val stats = Stats(population, _ticks)

  @Composable
  fun setup() {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(
      fontSize = 22.5.sp,
      color = Color.White,
    )

    val textToDraw = "Compose Rockets!"

    val textLayoutResult = remember(textToDraw) {
      textMeasurer.measure(textToDraw, textStyle)
    }

    _barriers.add(
      TextBarrier(
        text = textLayoutResult,
        position = Vector2((sceneWidth / 2) - (textLayoutResult.size.width / 2), getBarrierYPosition())
      )
    )

    reset()
    start()
  }

  private fun start() {
    val existingJob = sceneJob
    if (existingJob != null && existingJob.isActive) {
      return
    }

    sceneJob = sceneScope.launch {
      while (isActive) {
        if (resetCheck()) {
          reset()
          continue
        }

        population.rockets.forEach { rocket ->
          rocket.fly(_ticks.value)

          if (rocket.position.x < 0 || rocket.position.x > sceneWidth || rocket.position.y < 0 || rocket.position.y > sceneHeight) {
            rocket.death()
          }

          _barriers.forEach { barrier ->
            val isCollided = rocketBarrier(rocket, barrier)
            if (isCollided) {
              rocket.death()
            }
          }
        }

        tick()
        stats.update()
        delay(TICK_RATIO)
      }
    }
  }

  private fun tick() {
    _ticks.value++
  }

  fun stop() {
    sceneJob?.cancel()
    sceneJob = null
  }

  private fun reset() {
    target = target.copy(
      position = Vector2(sceneWidth / 2, getTargetYPosition())
    )

    population.evaluate()
    population.selection()

    population.rockets.forEach { rocket -> rocket.reset(sceneWidth / 2, sceneHeight) }
    _ticks.value = 0
    stats.reset()
  }

  private fun resetCheck(): Boolean {
    return _ticks.value >= GAME_TICKS_LIMIT - 1 ||
      population.rockets.all { rocket -> !rocket.isAlive || rocket.isTargetReached }
  }

  private fun getTargetYPosition(): Float = sceneHeight * 0.2f

  private fun getBarrierYPosition(): Float = sceneHeight * 0.4f

  companion object {
    const val ROCKETS_SIZE = 25
    const val GAME_TICKS_LIMIT = 400
    const val TICK_RATIO = 1_000L / 60
  }
}
