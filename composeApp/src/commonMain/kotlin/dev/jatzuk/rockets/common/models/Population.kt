package dev.jatzuk.rockets.common.models

class Population(size: Int, target: Target, private val sceneWidth: Int, private val sceneHeight: Int) {

  private var _rockets = MutableList(size) { Rocket(target, sceneWidth = sceneWidth, sceneHeight = sceneHeight) }
  val rockets get() = _rockets.toList()

  private val matingPool = mutableListOf<Rocket>()

  fun evaluate(): Pair<Int, Int> {
    var maxFitness = 0
    var avgFitness = 0

    rockets.forEach { rocket ->
      rocket.calculateFitness()
      avgFitness += rocket.fitness

      if (rocket.fitness > maxFitness) {
        maxFitness = rocket.fitness
      }
    }

    avgFitness /= rockets.size

    matingPool.clear()
    for (i in rockets.indices) {
      matingPool.add(rockets[i])

      val presence = (rockets[i].fitness * PRESENCE_RATIO).toInt()
      repeat(presence) {
        matingPool.add(rockets[i])
      }

      val successors = rockets.count { rocket -> rocket.isTargetReached }
      repeat(successors) {
        matingPool.add(rockets[i])
      }
    }

    return maxFitness to avgFitness
  }

  fun selection() {
    val newRockets = mutableListOf<Rocket>()
    matingPool.shuffle()

    for (i in rockets.indices) {
      val firstParent = matingPool.random().dna
      val secondParent = matingPool.random().dna

      val child = firstParent.crossover(secondParent)
      newRockets.add(Rocket(rockets[i].target, child, sceneWidth, sceneHeight))
    }
    _rockets = newRockets
  }

  private companion object {
    const val PRESENCE_RATIO = 0.5f
  }
}
