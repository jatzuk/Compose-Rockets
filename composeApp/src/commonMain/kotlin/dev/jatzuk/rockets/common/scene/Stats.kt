package dev.jatzuk.rockets.common.scene

import dev.jatzuk.rockets.common.models.Population
import kotlinx.coroutines.flow.StateFlow

class Stats(private val population: Population, val ticks: StateFlow<Int>) {

  var generationCount = 0
    private set

  var reachedTarget = 0
    private set

  var bestFitness = 0
    private set

  var averageFitness = 0
    private set

  var aliveRockets = 0
    private set

  var deathRockets = 0
    private set

  fun reset(bestFitness: Int, averageFitness: Int) {
    this.bestFitness = bestFitness
    this.averageFitness = averageFitness

    aliveRockets = population.rockets.size
    deathRockets = 0
    reachedTarget = 0

    generationCount++
  }

  fun update() {
    aliveRockets = population.rockets.count { rocket -> rocket.isAlive }
    deathRockets = population.rockets.size - aliveRockets
    reachedTarget = population.rockets.count { rocket -> rocket.isTargetReached }
  }
}
