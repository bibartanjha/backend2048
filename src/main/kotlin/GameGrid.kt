import kotlin.random.Random

class GameGrid {

    private var tiles: MutableList<MutableList<Int>> =
        MutableList(DIMSIZE) { MutableList(DIMSIZE) { 0 } }

    init {
        tiles[Random.nextInt(DIMSIZE)][Random.nextInt(DIMSIZE)] = 2
    }

    /**
     * Note to self:
     * These next two functions are brute-force because
     * their time-complexity is O(n^2) and space complexity is O(n^2).
     * The space complexity is O(n^2) because we are creating a whole new 2d array object
     */
    private fun rotateClockwise_brute_force_solution() {
        val newGrid = mutableListOf<MutableList<Int>>()

        for (row in 0..< DIMSIZE) {
            val newGridRow = mutableListOf<Int>()
            for (col in 0..< DIMSIZE) {
                newGridRow.add(tiles[(DIMSIZE - 1) - col][row])
            }
            newGrid.add(newGridRow)
        }

        tiles = newGrid
    }

    private fun rotateCounterClockwise_brute_force_solution() {
        val newGrid = mutableListOf<MutableList<Int>>()

        for (row in 0..<DIMSIZE) {
            val newGridRow = mutableListOf<Int>()
            for (col in 0..<DIMSIZE) {
                newGridRow.add(tiles[col][(DIMSIZE - 1) - row])
            }
            newGrid.add(newGridRow)
        }

        tiles = newGrid
    }

    /**
     * Note to self: These two functions are more efficient than the respective brute force solutions
     * While the time-complexity is O(n^2),
     * the space complexity is less than O(n^2) because we're not creating a whole new grid.
     */
    private fun rotateClockwise() {
        transpose()
        reverseRows()
    }

    private fun rotateCounterClockwise() {
        reverseRows()
        transpose()
    }

    private fun transpose() {
        var tempValue = 0
        for (row in 0..<DIMSIZE) {
            for (col in (row + 1)..<DIMSIZE) {
                tempValue = tiles[row][col]
                tiles[row][col] = tiles[col][row]
                tiles[col][row] = tempValue
            }
        }
    }

    private fun reverseRows() {
        for (row in tiles.indices) {
            tiles[row].reverse()
        }
    }

    fun printTiles() {
        for (row in tiles) {
            for (elem in row) {
                if (elem == 0) {
                    print("_ ")
                } else {
                    print("$elem ")
                }
            }
            println()
        }
    }

    fun updateTile(row: Int, col: Int, valToAdd: Int) {
        tiles[row][col] = valToAdd
    }

    //Note to self: can use this function to see where the 0s are and how many 2048s there are
    fun getAllTilesWithValue(valueToSearchFor: Int): MutableList<Pair<Int, Int>> {
        val indicesWithValue = mutableListOf<Pair<Int, Int>>()
        for (row in tiles.indices) {
            for (col in tiles[row].indices) {
                if (tiles[row][col] == valueToSearchFor) {
                    indicesWithValue.add(Pair(row, col))
                }
            }
        }
        return indicesWithValue
    }

    fun moveTilesUpAndMerge() {
        rotateCounterClockwise()
        moveTilesLeftAndMerge()
        rotateClockwise()
    }

    fun moveTilesDownAndMerge() {
        rotateClockwise()
        moveTilesLeftAndMerge()
        rotateCounterClockwise()
    }

    fun moveTilesRightAndMerge() {
        rotateClockwise()
        rotateClockwise()
        moveTilesLeftAndMerge()
        rotateClockwise()
        rotateClockwise()
    }

    fun moveTilesLeftAndMerge() {
        var newRow: MutableList<Int>
        for (row in tiles.indices) {
            newRow = MutableList(DIMSIZE) { 0 }
            var currIndexInNewRow = 0
            for (col in tiles[row].indices) {
                val tileValue = tiles[row][col]
                if (tileValue != 0) {
                    newRow[currIndexInNewRow] = tileValue
                    currIndexInNewRow += 1
                }
            }

            var i = 0
            while (i < newRow.size - 1) {
                if (newRow[i] == newRow[i + 1]) {
                    newRow[i] *= 2
                    newRow[i + 1] = 0
                    i += 1
                }
                i += 1
            }

            val newRow2 = MutableList(DIMSIZE) { 0 }
            var currIndexInNewRow2 = 0
            for (col in newRow.indices) {
                val tileValue = newRow[col]
                if (tileValue != 0) {
                    newRow2[currIndexInNewRow2] = tileValue
                    currIndexInNewRow2 += 1
                }
            }

            tiles[row] = newRow2
        }
    }

    fun containsAdjacentTilesWithEqualValue(): Boolean {
        for (row in tiles.indices) {
            for (col in tiles[row].indices) {
                if ((row + 1) < tiles.size) {
                    if (tiles[row][col] == tiles[row + 1][col]) {
                        return true
                    }
                }
                if ((col + 1) < tiles[row].size) {
                    if (tiles[row][col] == tiles[row][col + 1]) {
                        return true
                    }
                }
            }
        }
        return false
    }

    companion object {
        private const val DIMSIZE = 4
    }
}