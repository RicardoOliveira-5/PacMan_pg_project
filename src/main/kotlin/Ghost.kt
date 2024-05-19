import pt.isel.canvas.BLUE
import pt.isel.canvas.playSound

data class Ghost(val cell: Cell, val dir: Direction, val color: COLOURS)
enum class COLOURS { PINK, RED, CYAN, YELLOW, WHITE, BLUE}
val colours = listOf<COLOURS>(COLOURS.PINK, COLOURS.RED, COLOURS.CYAN, COLOURS.YELLOW)

/**
 * Checks if Pacman eats any ghosts in the lists of ghosts
 * adds 200 to the score if eaten
 */

fun Game.PacManEatGhost(): Game {
    val newGhosts = ghosts.filter { it.cell.col == pacman.cell.col && it.cell.line == pacman.cell.line }
    if(newGhosts.isNotEmpty()) {
        if (newGhosts.first().color == COLOURS.BLUE || newGhosts.first().color == COLOURS.WHITE)
            return copy(
                ghosts = ghosts.filterNot { it.cell.col == pacman.cell.col  && it.cell.line == pacman.cell.line  },
                score = score + 200
            )
        else {
            return copy(
                ghosts= ghosts.filterNot { pacman.cell.col == it.cell.col && pacman.cell.line == it.cell.line}
                , gameOver = true)
        }
    }
    else return copy()
}

/**
 * Moves the ghosts
 * if ghost is trapped in the maze we force the ghost to go to the opposite direction
 *
 */

fun Game.GhostsMove(): Game {
    val newGhosts: MutableList<Ghost> = mutableListOf()
    ghosts.forEach { ghost ->
        val dir: MutableList<Direction> = mutableListOf()
        if(!walls.any{ ghost.cell.col + 1 == it.col && ghost.cell.line == it.line} && ghost.dir!=Direction.LEFT)
            dir.add(Direction.RIGHT)
        if(!walls.any{ ghost.cell.col - 1 == it.col && ghost.cell.line == it.line} && ghost.dir!=Direction.RIGHT)
            dir.add(Direction.LEFT)
        if(!walls.any{ ghost.cell.col == it.col && ghost.cell.line - 1 == it.line} && ghost.dir!=Direction.DOWN)
            dir.add(Direction.UP)
        if(!walls.any{ ghost.cell.col == it.col && ghost.cell.line + 1 == it.line} && ghost.dir!=Direction.UP)
            dir.add(Direction.DOWN)

        if (dir.isEmpty()) dir.add(
            when (ghost.dir){
                Direction.DOWN -> Direction.UP
                Direction.UP -> Direction.DOWN
                Direction.LEFT -> Direction.RIGHT
                else -> Direction.LEFT
            })
        println(dir)


        when(dir.random()) {
            Direction.RIGHT -> {
                if (ghost.cell.col + 1 < GRID_WIDTH) newGhosts.add(Ghost(Cell(ghost.cell.col + 1, ghost.cell.line, ghost.cell.food, ghost.cell.superfood,ghost.cell.fruit, ghost.cell.fruitType), Direction.RIGHT,ghost.color))
                else newGhosts.add(Ghost(transitCell(ghost.dir,ghost.cell), Direction.RIGHT,ghost.color))
            }

            Direction.LEFT -> {
                if (ghost.cell.col - 1 >= 0) newGhosts.add(Ghost(Cell(ghost.cell.col - 1, ghost.cell.line, ghost.cell.food, ghost.cell.superfood,ghost.cell.fruit, ghost.cell.fruitType), Direction.LEFT,ghost.color))
                else newGhosts.add(Ghost(transitCell(ghost.dir,ghost.cell), Direction.LEFT ,ghost.color))
            }

            Direction.UP -> {
                if (ghost.cell.line - 1 >= 0) newGhosts.add(Ghost(Cell(ghost.cell.col, ghost.cell.line - 1, ghost.cell.food, ghost.cell.superfood,ghost.cell.fruit, ghost.cell.fruitType), Direction.UP,ghost.color))
                else newGhosts.add(Ghost(transitCell(ghost.dir,ghost.cell), Direction.UP ,ghost.color))
            }

            else -> { // DOWN
                if (ghost.cell.line + 1 < GRID_HEIGHT-1) newGhosts.add(Ghost(Cell(ghost.cell.col, ghost.cell.line + 1, ghost.cell.food, ghost.cell.superfood,ghost.cell.fruit, ghost.cell.fruitType), Direction.DOWN, ghost.color))
                else newGhosts.add(Ghost(transitCell(ghost.dir,ghost.cell), Direction.DOWN ,ghost.color))
            }
        }
    }
    return copy(ghosts = newGhosts.toList())

}

/**
 * Choose the ghost colour according to the pacman movements
 */


fun Game.ghostControl(): Game {
    return if(ghosts.any { it.color == COLOURS.BLUE || it.color == COLOURS.WHITE })
        if(pacmanMovements == 30)
            copy(pacmanMovements = 0, ghosts = ghosts.map { Ghost(it.cell,it.dir,colours.random()) })
        else if(pacmanMovements >= 25)
            copy(pacmanMovements = pacmanMovements + 1, ghosts = ghosts.map { Ghost(it.cell,it.dir, COLOURS.BLUE) })
        else
            copy(pacmanMovements = pacmanMovements + 1, ghosts = ghosts.map { if(it.color == COLOURS.BLUE) Ghost(it.cell,it.dir, COLOURS.WHITE) else it})
    else
        copy()
}

/**
 * Adds a new ghost to the lists of ghosts
 */

fun Game.generateGhost(): Game = copy(ghosts = ghosts + Ghost(loadMap(maze).cells.getCells(CellType.GHOST_START).random(),Direction.CENTER, colours.random()))
