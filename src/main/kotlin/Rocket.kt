import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Rocket {

    private val _position = MutableStateFlow(Vector2(0, 0))
    val position = _position.asStateFlow()

    private val _direction = MutableStateFlow(Vector2(0, 0))
    val direction = _direction.asStateFlow()

    var velocity = 1

    fun setPosition(vector2: Vector2) {
        _position.value = vector2
    }

    fun setDirection(vector2: Vector2) {
        _direction.value = vector2
    }

    fun fly() {
        _position.value += Vector2(0,  -velocity)
    }

    companion object {

        const val WIDTH = 25f
        const val HEIGHT = 100f
    }
}