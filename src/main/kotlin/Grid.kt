import pt.isel.canvas.*


data class Dimension(val width: Int, val height: Int)


fun Cell.inArena() =
    line in 0 .. GRID_WIDTH - 1 && col in 0 until  GRID_HEIGHT - 1



enum class Direction(val lineDif: Int, val colDif: Int) {
    LEFT(0,-CELL_SIZE), RIGHT(0,+CELL_SIZE), UP(-CELL_SIZE,0), DOWN(+CELL_SIZE,0), CENTER (0,0)
}


fun Int.toDir(): Direction? = when (this) {
    RIGHT_CODE -> Direction.RIGHT
    LEFT_CODE -> Direction.LEFT
    UP_CODE -> Direction.UP
    DOWN_CODE -> Direction.DOWN
    else -> null
}