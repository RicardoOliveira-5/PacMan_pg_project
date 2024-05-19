import pt.isel.canvas.*

const val CELL_SIZE = 32
const val GRID_WIDTH = 11
const val GRID_HEIGHT = 12
const val PERIOD_TIME_OF_GHOST = 20000
const val PERIOD_TIME_OF_FRUIT = 30000
const val INICIAL_SCORE = 0


fun main() {
    onStart {
        val cv = Canvas(GRID_WIDTH*CELL_SIZE,GRID_HEIGHT*CELL_SIZE, BLACK)
        var game = createGame()
        cv.drawArena(game)

        cv.onKeyPressed { key: KeyEvent ->
            if(!game.gameOver) {
                game = game.movePacMan(key.code)
                game = game.eatPellets()
                game = game.PacManEatGhost()
                game = game.ghostControl()
                game = game.GhostsMove()
                game = game.WhenEatFruit()
                game = game.checkisGameOver()
                println(game.gameOver)
                cv.drawArena(game)
            }
        }

        cv.onTimeProgress(PERIOD_TIME_OF_GHOST) {
            if(!game.gameOver) {
                game = game.generateGhost()
                cv.drawArena(game)
            }
        }

        cv.onTimeProgress(PERIOD_TIME_OF_FRUIT) {
            if(!game.gameOver) {
                game = game.generateFruit()
                cv.drawArena(game)
            }
        }


    }
    onFinish {  }
}
