
data class PacMan(val cell: Cell, val dir: Direction)

/**
 * Moves pacman with keycode on the input
 */

fun Game.movePacMan(keyCode: Int): Game {
    val pac = pacman.moveByKey(keyCode, this).getCellFood(this)
    return copy(pacman = pac)
}

/**
 * Obtains the value of food in the respective Cell
 */

fun PacMan.getCellFood(g: Game): PacMan {
    val getPacCellInPelletsList: List<Cell> = g.pellets.filter {
        it.col == cell.col &&
                it.line == cell.line
    }
    return PacMan(Cell(cell.col, cell.line, getPacCellInPelletsList.first().food, getPacCellInPelletsList.first().superfood,getPacCellInPelletsList.first().fruit, getPacCellInPelletsList.first().fruitType), dir)
}

/**
 * Pacman is moved according to the direction
 * function toDir transforms a keycode into a direction
 */

fun PacMan.moveByKey(keyCode: Int, g: Game): PacMan {
    val dir:Direction = keyCode.toDir() ?: return this

    val pos = when(dir){
        Direction.UP -> Cell(cell.col, cell.line - 1, cell.food, cell.superfood, cell.fruit, cell.fruitType)

        Direction.DOWN -> Cell(cell.col, cell.line + 1, cell.food, cell.superfood, cell.fruit, cell.fruitType)

        Direction.LEFT -> Cell(cell.col - 1, cell.line, cell.food, cell.superfood, cell.fruit, cell.fruitType)

        Direction.RIGHT -> Cell(cell.col + 1, cell.line, cell.food, cell.superfood, cell.fruit, cell.fruitType)

        else -> cell
    }
    if (pos.inArena())
        return if (g.validMovement(pos))
            PacMan(cell, Direction.CENTER)
        else PacMan(pos,dir)
    else {
       return PacMan(g.transitCell(dir,cell), dir)
   }
}

/**
 * Checks if the new Cell is going to be a wall
 */


fun Game.validMovement(pos: Cell): Boolean = walls.any { it.col == pos.col && it.line == pos.line }

/**
 * this function makes either the Ghost or the Pacman moves to the other side of the map when they are  on the edge
 * Example: Dir.Down -> Returns the first Cell in the first possible line but with the same colune
 */

fun Game.transitCell(dir: Direction, cell: Cell): Cell = when(dir) {
    Direction.DOWN -> pellets.first { it.col == cell.col }
    Direction.UP -> pellets.last { it.col == cell.col }
    Direction.LEFT -> pellets.last { it.line == cell.line }
    else -> pellets.first { it.line == cell.line }
}


operator fun Cell.plus(dir: Direction): Cell = Cell(col+dir.colDif, line+dir.lineDif, food, superfood, fruit, fruitType)

/**
 * Checks if pacman is in the same cell as a food
 * if Pacman is in the same cell with superfood Score = score + 50 and put the lists of ghosts in white mode (run mode )
 */

fun Game.eatPellets(): Game {
    if(pacman.dir == Direction.CENTER)
        return copy()
    if(pacman.cell.food) {
        if(pacman.cell.superfood)
            return copy(
                pellets = pellets.map {
                    if (it.col == pacman.cell.col && it.line == pacman.cell.line)
                        Cell(it.col,it.line,false, false, false, it.fruitType)
                    else
                        it
                },
                score = score + 50,
                ghosts = ghosts.map { Ghost(it.cell,it.dir,COLOURS.WHITE) }

            )
        else
            return copy(
                pellets = pellets.map {
                    if (it.col == pacman.cell.col && it.line == pacman.cell.line)
                        Cell(it.col,it.line,false, false,false, it.fruitType)
                    else
                        it
                },
                score = score + 10
            )
    }
    if(score==0) return copy()
    return copy(score = score - 1)
}
