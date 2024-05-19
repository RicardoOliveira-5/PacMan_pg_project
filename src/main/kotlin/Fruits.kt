enum class FruitTypes {CHERRY, BANANA, STRAWBERRY, ORANGE, APPLE, MELON, NONE}
val Fruit_Types = listOf(FruitTypes.CHERRY, FruitTypes.BANANA, FruitTypes.STRAWBERRY, FruitTypes.ORANGE, FruitTypes.APPLE,FruitTypes.MELON)

/**
 * Generates a food inbto the Game
 * If there is a food, it wont draw another one, returns the Game as it was
 */

fun Game.generateFruit(): Game {
    if (!pellets.any { it.fruitType != FruitTypes.NONE && it.fruit}) return copy( pellets = pellets.map { if (!it.fruit) it else Cell(it.col, it.line, it.food, it.superfood, it.fruit, Fruit_Types.random())})
    return copy()
}

/**
 * What happens when Pacman eats the fruit
 */

fun Game.WhenEatFruit():Game {
    if (pacman.cell.fruitType != FruitTypes.NONE) {
        val points = when (pacman.cell.fruitType) {
            FruitTypes.CHERRY -> 100
            FruitTypes.BANANA -> 200
            FruitTypes.STRAWBERRY -> 300
            FruitTypes.ORANGE -> 500
            FruitTypes.APPLE -> 700
            else -> 1000 // MELON
        }
        return copy(
            pellets = pellets.map {
                if (it.col == pacman.cell.col && it.line == pacman.cell.line)
                    Cell(it.col, it.line, false, false, true, FruitTypes.NONE)
                else
                    it },
            score = score + points
        )
    }
    return copy()
}
