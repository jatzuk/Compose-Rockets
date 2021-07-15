package scene

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import math.Vector2
import models.Barrier
import models.Population
import models.Target

private const val ROCKETS_SIZE = 25

class Scene(private val width: Float, private val height: Float) {

    private val sceneScope = CoroutineScope(Dispatchers.IO)
    private var sceneJob: Job? = null

    private val _ticks = MutableStateFlow(0)

    val target = Target(Vector2())
    val population = Population(ROCKETS_SIZE, target)
    val barriers = listOf(
        Barrier(Vector2(width / 2 - 100, height / 2 + 70)),
        Barrier(Vector2(width / 2 + 100, height / 2)),
        Barrier(Vector2(width / 2, height / 2 - 100)),
    )

    val stats = Stats(population, _ticks.asStateFlow())

    init {
        WIDTH = width
        HEIGHT = height
    }

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

               population.rockets.forEach { rocket ->
                    rocket.fly(_ticks.value)
                    if (rocket.position.x < 0 || rocket.position.x > width || rocket.position.y < 0 || rocket.position.y > height) {
                        rocket.death()
                        stats.update()
                    }

                   barriers.forEach { barrier ->
                       if (rocket.position.x == barrier.position.x || barrier.position.y == rocket.position.y) {
                           rocket.death()
                           stats.update()
                       }
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
        target.apply {
//            radius = Random.nextFloat() * 100
            position = Vector2(width / 2, radius)
        }

        population.evaluate()
        stats.reset()
        population.selection()

        population.rockets.forEach { rocket -> rocket.reset(width / 2, height) }
        _ticks.value = 0
    }

    private fun resetCheck() = _ticks.value == GAME_TICKS_LIMIT - 1 || stats.aliveRockets == 0

    companion object {

        const val GAME_TICKS_LIMIT = 200
        const val TICK_RATIO = 1000L / 60

        var WIDTH = 0f
        private set

        var HEIGHT = 0f
        private set
    }
}
