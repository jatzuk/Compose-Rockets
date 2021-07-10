package models

import geometry.Vector2
import geometry.randomVector

class DNA(length: Int, genes: List<Vector2>? = null) {

    private val _genes = mutableListOf<Vector2>()
    val genes get() = _genes.toList()

    init {
        if (genes == null) fillRandomly(length)
        else {
            _genes.clear()
            _genes.addAll(genes)
        }
    }


    private fun fillRandomly(length: Int) {
        repeat(length) {
            _genes.add(randomVector(-1.0, 1.0))
        }
    }
}
