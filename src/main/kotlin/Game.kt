
data class Game(
    val dim: Dimension, val pacman: PacMan,
    val pellets: List<Cell>, val score: Int,
    val walls: List<Cell>, val ghosts: List<Ghost>,
    val pacmanMovements: Int, val gameOver:Boolean
)
/**
Creates the inicial Game with
  *  dimensions,
  *  pacman inicial position,
  *  where to drawPellets,
  *  the inicial score=0,
 * Walls creates them by going to the maze list and taking '#' as the wall and then proceeds to create the wall cells(createCells)


 */

fun createGame(): Game {
    val getPacManInicialPos = loadMap(maze).cells.getCells(CellType.PACMAN_START)[0]
    return Game (
        dim = Dimension(GRID_WIDTH,GRID_HEIGHT),
        pacman = PacMan(Cell(getPacManInicialPos.col ,getPacManInicialPos.line ,getPacManInicialPos.food, getPacManInicialPos.superfood, getPacManInicialPos.fruit, getPacManInicialPos.fruitType), Direction.CENTER),
        pellets = loadMap(maze).cells.getCells(CellType.PELLET),
        score = 0,
        walls = loadMap(maze).cells.getCells(CellType.WALL),
        ghosts = getGhosts(),
        pacmanMovements = INICIAL_SCORE,
        gameOver = false
    )
}

/**
 * Generates mutable list of ghosts which is added with a new ghost with a diferent colour
 */

fun getGhosts(): List<Ghost> {
    val list: MutableList<Ghost> = mutableListOf()
    loadMap(maze).cells.getCells(CellType.GHOST_START).forEach {
        list.add(Ghost(it,Direction.CENTER, colours.random()))
    }
    return list.toList()
}

/**
 * Gets the respective Cell as maze is drawn
 */

fun List<Element>.getCells(type: CellType): List<Cell> {
    val mutableCells: MutableList<Cell> = mutableListOf()
    when (type) {
        CellType.WALL ->
            this.forEach {
                if (it.type == CellType.WALL)
                    mutableCells.add(it.cell)
            }
        CellType.PACMAN_START ->
            this.forEach {
                if (it.type == CellType.PACMAN_START)
                    mutableCells.add(it.cell)
            }
        CellType.GHOST_START ->
            this.forEach {
                if (it.type == CellType.GHOST_START)
                    mutableCells.add(it.cell)
            }
        else ->
            this.forEach {
                if (it.type != CellType.WALL)
                    mutableCells.add(it.cell)
            }
    }
    return mutableCells
}

/**
 * It will be gameover if gameover = true
 * if is not game over is because List of cell still have a fruit, pellet or superfood
 */

fun Game.checkisGameOver():Game{
    return copy( gameOver = if (gameOver) true else !pellets.any { it.food || it.superfood || (it.fruit && it.fruitType != FruitTypes.NONE)})
}