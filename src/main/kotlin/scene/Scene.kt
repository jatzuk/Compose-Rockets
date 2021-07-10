package scene

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val ROCKETS_SIZE = 50

class Scene(private val width: Float, private val height: Float) {

    private val sceneScope = CoroutineScope(Dispatchers.IO)
    private var sceneJob: Job? = null

    private val _ticks = MutableStateFlow(0)
    val ticks = _ticks.asStateFlow()

    val rockets = Array(ROCKETS_SIZE) { Rocket() }

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
                if (ticks.value == GAME_TICKS_LIMIT - 1) {
                    reset()
                }

                rockets.forEach { rocket -> rocket.fly() }
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

        _ticks.value = 0
    }

    companion object {

        const val GAME_TICKS_LIMIT = 1_000
        const val TICK_RATIO = 1000L / 60
    }
}
