package scene

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import models.Rocket

private const val ROCKETS_SIZE = 25

class Scene(private val width: Float, private val height: Float) {

    private val sceneScope = CoroutineScope(Dispatchers.IO)
    private var sceneJob: Job? = null

    private val _ticks = MutableStateFlow(0)
    val ticks = _ticks.asStateFlow()

    val rockets = Array(ROCKETS_SIZE) { Rocket() }

    val stats = Stats(rockets, ticks)

    fun setup() {
        reset()
        start()
    }

    fun tick() {
        _ticks.value++
    }

    fun start() {
        sceneJob = sceneScope.launch {
            while (isActive) {
                if (resetCheck()) {
                    reset()
                }

                rockets.forEach { rocket ->
                    rocket.fly()
                    if (rocket.position.x < 0 || rocket.position.x > width || rocket.position.y < 0 || rocket.position.y > height) {
                        rocket.death()
                        stats.update()
                    }
                }
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
        rockets.forEach { rocket -> rocket.reset(width / 2, height) }
        stats.reset()
        _ticks.value = 0
    }

    private fun resetCheck() = ticks.value == GAME_TICKS_LIMIT - 1 || stats.aliveRockets.value == 0

    companion object {

        const val GAME_TICKS_LIMIT = 200
        const val TICK_RATIO = 1000L / 60
    }
}
