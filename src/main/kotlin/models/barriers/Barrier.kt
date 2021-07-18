package models.barriers

import math.Vector2

interface Barrier {

    val position: Vector2

    val width: Int
    val height: Int
}
