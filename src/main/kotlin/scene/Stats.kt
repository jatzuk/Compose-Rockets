package scene

import kotlinx.coroutines.flow.StateFlow
import models.Population

class Stats(private val population: Population, val ticks: StateFlow<Int>) {

  var populationCount = 0
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

  fun reset() {
    bestFitness = population.rockets.maxOf { rocket -> rocket.fitness }
    averageFitness = population.rockets.sumOf { rocket -> rocket.fitness } / population.rockets.size

    aliveRockets = population.rockets.size
    deathRockets = 0
    reachedTarget = 0

    populationCount++
  }

  fun update() {
    aliveRockets = population.rockets.count { rocket -> rocket.isAlive }
    deathRockets = population.rockets.size - aliveRockets
    reachedTarget = population.rockets.count { rocket -> rocket.isTargetReached }
  }
}
