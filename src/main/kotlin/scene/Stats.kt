package scene

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import models.Rocket

class Stats(private val rockets: Array<Rocket>, val ticks: StateFlow<Int>) {

    private val _populationCount = MutableStateFlow(0)
    val populationCount = _populationCount.asStateFlow()

    private val _aliveRockets = MutableStateFlow(rockets.size)
    val aliveRockets = _aliveRockets.asStateFlow()

    private val _deathRockets = MutableStateFlow(0)
    val deathRockets = _deathRockets.asStateFlow()

    fun reset() {
        _aliveRockets.value = rockets.size
        _deathRockets.value = 0

        _populationCount.value++
    }

    fun update() {
        val aliveSize = rockets.count { rocket -> rocket.isAlive }
        _aliveRockets.value = aliveSize
        _deathRockets.value = rockets.size - aliveSize
    }
}
