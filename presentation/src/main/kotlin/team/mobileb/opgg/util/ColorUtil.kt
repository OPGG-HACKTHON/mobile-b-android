package team.mobileb.opgg.util

object ColorUtil {
    private val positionColors = hashMapOf<Int, Int>()

    init {
        repeat(5) { index ->
            positionColors[index + 1] = randomColor
        }
    }

    fun getColorForPosition(index: Int) = positionColors[index]!!

    private val randomColor get() = (Math.random() * 16777215).toInt() or (0xFF shl 24)
}
