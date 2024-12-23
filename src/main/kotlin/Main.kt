var gameContinue = true

var gameGrid = GameGrid()


fun playGame() {
    println("i = up, j = left, k = down, l = right, q = quit")
    var userInput: String?
    while(gameContinue) {
        gameGrid.printTiles()

        // Game logic
        userInput = readlnOrNull()
        if (userInput == "i") {
            gameGrid.moveTilesUpAndMerge()
        } else if (userInput == "j") {
            gameGrid.moveTilesLeftAndMerge()
        } else if (userInput == "k") {
            gameGrid.moveTilesDownAndMerge()
        } else if (userInput == "l") {
            gameGrid.moveTilesRightAndMerge()
        } else if (userInput == "q") {
            gameContinue = false
            println("Game quit")
            continue
        } else {
            println("Please enter a valid input")
            continue
        }


        val tilesWith2048 = gameGrid.getAllTilesWithValue(2048)
        if (tilesWith2048.isNotEmpty()) {
            gameContinue = false
            println("Congratulations! You got to 2048")
        } else {
            val tilesWithZero = gameGrid.getAllTilesWithValue(0)

            if (tilesWithZero.isEmpty() && (!gameGrid.containsAdjacentTilesWithEqualValue())) {
                gameContinue = false
                println("Game over!")
            } else {
                val randomTile = tilesWithZero.random()
                println()
                println("New tile added at $randomTile")
                gameGrid.updateTile(randomTile.first, randomTile.second, 2)
            }
        }
    }

    println()
    println("Final Board: ")
    gameGrid.printTiles()
}

fun main(args: Array<String>) {
    playGame()
}