package models

import math.Vector2
import math.randomVector
import kotlin.random.Random

class DNA(length: Int, genes: List<Vector2>? = null) {

    val genes = genes ?: createRandom(length)

    fun crossover(partner: DNA): DNA {
        val newGenes = mutableListOf<Vector2>()
        val mid = Random.nextInt(genes.size)

        for (i in genes.indices) {
            val gene = if (i > mid) genes[i] else partner.genes[i]
            newGenes.add(gene.mutate())
        }

        return DNA(genes.size, newGenes)
    }

    private fun Vector2.mutate(): Vector2 {
        if (Random.nextFloat() < 0.01) {
            val vector2 = randomVector(-1.0, 1.0)
            vector2.setMagnitude(Random.nextFloat() * 2 - 1)
        }
        return this
    }

    private fun createRandom(length: Int): List<Vector2> {
        val genes = mutableListOf<Vector2>()
        repeat(length) { i ->
            genes.add(randomVector(-1.0, 1.0))
            genes[i].setMagnitude(Random.nextFloat())
        }
        return genes
    }
}
