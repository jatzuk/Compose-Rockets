import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Scene(val width: Int, val height: Int) {

    private val sceneScope = CoroutineScope(Dispatchers.IO)

    private val _ticks = MutableStateFlow(0)
    val ticks = _ticks.asStateFlow()

    var rocket = Rocket()
    private set

    fun setup() {
        rocket.setPosition(Vector2(width / 2, height - 100))
        start()
    }

    fun tick() {
        _ticks.value++
    }

    fun start() {
        sceneScope.launch {
            while (ticks.value < GAME_TICKS_LIMIT) {
                if (ticks.value == GAME_TICKS_LIMIT - 1) {
                    reset()
                    start()
                }

                rocket.fly()
                tick()
                delay(TICK_RATIO)
            }
        }
    }

    fun reset() {
        rocket = Rocket()
        rocket.setPosition(Vector2(width / 2, height - 100))

        _ticks.value = 0
    }

    companion object {

         const val GAME_TICKS_LIMIT = 1_000
         const val TICK_RATIO = 1000L / 60
    }
}