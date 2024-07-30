package dev.jatzuk.rockets.common.scene

import dev.jatzuk.rockets.common.math.Vector2
import dev.jatzuk.rockets.common.math.rocketBarrier
import dev.jatzuk.rockets.common.models.Population
import dev.jatzuk.rockets.common.models.Target
import dev.jatzuk.rockets.common.models.barriers.TextBarrier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class Scene(sceneDimensions: SceneDimensions) {

  private val width = sceneDimensions.width
  private val height = sceneDimensions.height

  private val sceneScope = CoroutineScope(Dispatchers.Default)
  private var sceneJob: Job? = null

  private val _ticks = MutableStateFlow(0)

  val target = Target(Vector2())
  val population = Population(ROCKETS_SIZE, target, height.toInt())
  val barriers = listOf(
    TextBarrier("Compose Rockets!", 100f, Vector2(width / 4, 300f)),
  )

  val stats = Stats(population, _ticks.asStateFlow())

  fun setup() {
    reset()
    start()
  }

  private fun tick() {
    _ticks.value++
  }

  fun start() {
    sceneJob = sceneScope.launch {
      while (isActive) {
        if (resetCheck()) reset()

        population.rockets.forEach { rocket ->
          rocket.fly(_ticks.value)
          if (rocket.position.x < 0 || rocket.position.x > width || rocket.position.y < 0 || rocket.position.y > height) {
            rocket.death()
            stats.update()
          }

          barriers.forEach { barrier ->
            val isCollided = rocketBarrier(rocket, barrier)
            if (isCollided) {
              rocket.death()
              stats.update()
            }
          }
        }

        stats.update()
        tick()
        delay(TICK_RATIO)
      }
    }
  }

  fun stop() {
    sceneJob?.cancel()
    sceneJob = null
  }

  fun reset() {
    target.apply {
//            radius = Random.nextFloat() * 100
      position = Vector2(width / 2, radius * 2)
    }

    population.evaluate()
    stats.reset()
    population.selection()

    population.rockets.forEach { rocket -> rocket.reset(width / 2, height) }
    _ticks.value = 0
  }

  private fun resetCheck() =
    _ticks.value == GAME_TICKS_LIMIT - 1 || population.rockets.all { rocket -> !rocket.isAlive || rocket.isTargetReached }

  companion object {
    const val ROCKETS_SIZE = 25
    const val GAME_TICKS_LIMIT = 400
    const val TICK_RATIO = 1000L / 60
  }
}
