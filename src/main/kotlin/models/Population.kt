package models

class Population(size: Int, target: Target) {

    private var _rockets = MutableList(size) { Rocket(target) }
    val rockets get() = _rockets.toList()

    private val matingPool = mutableListOf<Rocket>()

    fun evaluate() {
        var maxFitness = 0

        rockets.forEach { rocket ->
            rocket.calculateFitness()
            if (rocket.fitness > maxFitness) maxFitness = rocket.fitness
        }

        matingPool.clear()
        for (i in rockets.indices) {
            matingPool.add(rockets[i])

            val presence = (rockets[i].fitness * 0.3).toInt()
            repeat(presence) {
                matingPool.add(rockets[i])
            }
        }
    }

    fun selection() {
        val newRockets = mutableListOf<Rocket>()
        matingPool.shuffle()

        for (i in rockets.indices) {
            val firstParent = matingPool.random().dna
            val secondParent = matingPool.random().dna

            val child = firstParent.crossover(secondParent)
            newRockets.add(Rocket(rockets[i].target, child))
        }
        _rockets = newRockets
    }
}
