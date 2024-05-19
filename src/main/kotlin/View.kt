import pt.isel.canvas.*

/**
 * what is going to be drawn in the arena
 */

fun Canvas.drawArena(g: Game) {
    erase()
    drawPellets(g.pellets)
    drawGhosts(g)
    drawtheWalls(g.walls)
    drawTheScore(g)
    drawFruit(g)
    if(g.gameOver) {
        drawGameOver()
        if (g.pellets.any { it.fruitType != FruitTypes.NONE || it.food || it.superfood }){
            drawDeadPacman(g)
        } else drawPacMan(g.pacman)
    }else{
        drawPacMan(g.pacman)
    }

}

/**
 * Draw the ghosts , going to pacman.png and cutting which ghost i want to draw
 */

fun Canvas.drawGhosts(game: Game) {
    game.ghosts.forEach {
        when(it.color) {
            COLOURS.RED -> drawGhostsColored(it,0)

            COLOURS.PINK -> drawGhostsColored(it,32)

            COLOURS.CYAN -> drawGhostsColored(it,64)

            COLOURS.YELLOW -> drawGhostsColored(it,96)

            COLOURS.BLUE -> drawImage("packman.png|160,64,30,30", it.cell.col * CELL_SIZE,it.cell.line * CELL_SIZE,CELL_SIZE,CELL_SIZE)
            COLOURS.WHITE -> drawImage("packman.png|160,96,30,30", it.cell.col * CELL_SIZE,it.cell.line * CELL_SIZE,CELL_SIZE,CELL_SIZE)
        }
    }
}

/**
 * Chooses the ghost according to pos
 */
fun Canvas.drawGhostsColored(it: Ghost, pos: Int) = when(it.dir) {
    Direction.UP ->drawImage("packman.png|$pos,64,30,30",it.cell.col*CELL_SIZE,it.cell.line*CELL_SIZE)
    Direction.DOWN ->drawImage("packman.png|$pos,96,30,30",it.cell.col*CELL_SIZE,it.cell.line*CELL_SIZE)
    Direction.LEFT ->drawImage("packman.png|$pos,128,30,30",it.cell.col*CELL_SIZE,it.cell.line*CELL_SIZE)
    Direction.RIGHT ->drawImage("packman.png|$pos,160,30,30",it.cell.col*CELL_SIZE,it.cell.line*CELL_SIZE)
    Direction.CENTER ->drawImage("packman.png|$pos,96,30,30",it.cell.col*CELL_SIZE,it.cell.line*CELL_SIZE)
}

/**
 * Draws Pacman into Canvas according to the direction given
 */
fun Canvas.drawPacMan(p: PacMan) {
    when (p.dir) {
        Direction.LEFT -> drawImage("packman.png|0,32,30,30", p.cell.col * CELL_SIZE,p.cell.line * CELL_SIZE,CELL_SIZE,CELL_SIZE)
        Direction.RIGHT -> drawImage("packman.png|32,32,30,30", p.cell.col * CELL_SIZE,p.cell.line * CELL_SIZE,CELL_SIZE,CELL_SIZE)
        Direction.DOWN -> drawImage("packman.png|64,32,30,30", p.cell.col * CELL_SIZE,p.cell.line * CELL_SIZE,CELL_SIZE,CELL_SIZE)
        Direction.UP -> drawImage("packman.png|96,32,30,30", p.cell.col * CELL_SIZE,p.cell.line * CELL_SIZE,CELL_SIZE,CELL_SIZE)
        Direction.CENTER -> drawImage("packman.png|128,32,30,30", p.cell.col * CELL_SIZE,p.cell.line * CELL_SIZE,CELL_SIZE,CELL_SIZE)
    }
}

/**
 * Draws the diferent types of fruit
 */


fun Canvas.drawFruit(g: Game) {
    g.pellets.forEach {
        when (it.fruitType) {
            FruitTypes.ORANGE -> drawImage("packman.png|288,160,30,30", it.col * CELL_SIZE, it.line * CELL_SIZE)
            FruitTypes.STRAWBERRY -> drawImage("packman.png|256,160,30,30", it.col * CELL_SIZE, it.line * CELL_SIZE)
            FruitTypes.APPLE -> drawImage("packman.png|320,128,30,30", it.col * CELL_SIZE, it.line * CELL_SIZE)
            FruitTypes.BANANA -> drawImage("packman.png|288,128,30,30", it.col * CELL_SIZE, it.line * CELL_SIZE)
            FruitTypes.CHERRY -> drawImage("packman.png|256,128,30,30", it.col * CELL_SIZE, it.line * CELL_SIZE)
            FruitTypes.MELON -> drawImage("packman.png|320,160,30,30", it.col * CELL_SIZE, it.line * CELL_SIZE)
        }
    }
}

/**
 * Draw the Walls into Canvas Window
 */

fun Canvas.drawtheWalls(elements: List<Cell>) {
    elements.forEach {
        drawRect(it.col * CELL_SIZE,it.line * CELL_SIZE,CELL_SIZE,CELL_SIZE, BLUE)
    }
}

/**
 * Draw the pallets into Canvas
 */

fun Canvas.drawPellets(cells: List<Cell>) {
    cells.forEach {
        if(it.food) {
            if (it.superfood)
                drawCircle(it.col * CELL_SIZE + CELL_SIZE / 2, it.line * CELL_SIZE + CELL_SIZE / 2, 8, WHITE)
            else
                drawCircle(it.col * CELL_SIZE + CELL_SIZE / 2, it.line * CELL_SIZE + CELL_SIZE / 2, 4, WHITE)
        }
    }
}
/**
 * Draw the Score into Canvas
 */


fun Canvas.drawTheScore(g: Game) {
    drawText(5,CELL_SIZE*GRID_HEIGHT-CELL_SIZE/2+7,"Score: ${g.score}", WHITE, 20)
}
/**
 * Draw the Pacman when dead into Canvas
 */
fun Canvas.drawDeadPacman(g: Game) {
    drawImage("packman.png|320,192,30,30",g.pacman.cell.col * CELL_SIZE,g.pacman.cell.line * CELL_SIZE,CELL_SIZE,CELL_SIZE)
}
/**
 * Draw the Words "Game Over" into Canvas
 */

fun Canvas.drawGameOver() = drawText(CELL_SIZE*GRID_WIDTH/2-CELL_SIZE-16,CELL_SIZE*GRID_HEIGHT-CELL_SIZE/2+7,"Game Over", RED, 17)


