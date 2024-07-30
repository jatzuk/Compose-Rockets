package dev.jatzuk.rockets.common.models

import dev.jatzuk.rockets.common.math.Vector2
import dev.jatzuk.rockets.common.math.randomVector
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

  private fun Vector2.mutate() = if (Random.nextFloat() < 0.01) createPredefinedVector2() else this

  private fun createRandom(length: Int): List<Vector2> {
    val genes = mutableListOf<Vector2>()
    repeat(length) {
      genes.add(createPredefinedVector2())
    }
    return genes
  }

  private fun createPredefinedVector2() = randomVector(VECTOR_MIN_VALUE, VECTOR_MAX_VALUE).apply {
    setMagnitude(VECTOR_MAGNITUDE)
  }

  private companion object {

    const val VECTOR_MAX_VALUE = 3.0
    const val VECTOR_MIN_VALUE = -VECTOR_MAX_VALUE
    const val VECTOR_MAGNITUDE = 0.5f
  }
}
