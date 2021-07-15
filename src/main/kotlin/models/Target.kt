package models

import math.Vector2

data class Target(var position: Vector2) {

    var radius = DEFAULT_RADIUS

    companion object {

        const val DEFAULT_RADIUS = 50f
    }
}
