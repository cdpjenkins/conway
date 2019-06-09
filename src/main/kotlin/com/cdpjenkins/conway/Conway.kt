package com.cdpjenkins.conway

const val CELL_WIDTH = 5
const val CELL_HEIGHT = 5


// Game shizzle here
class Game(val cells: Set<Cell>, val width: Int, val height: Int) {
    fun tick(): Game {
        val cellsPlusNeighbours = cells.union(cells.flatMap { it.neighbours() })
        val cells = cellsPlusNeighbours.filter { it.isAliveInNextGeneration(this) }.toSet()

        return Game(cells = cells, width = this.width, height = this.height)
    }

    fun println() {
        println(toPrettyString())
    }

    fun toPrettyString(): String =
        (0..height-1).map { y ->
            (0..width-1).map { x ->
                if (cells.contains(Cell(x, y))) 'x'
                else '.'
            }.joinToString("")
        }.joinToString("\n")

    fun isCellAlive(x: Int, y: Int): Boolean {
        return cells.contains(Cell(x, y))
    }
}

data class Cell(val x: Int, val y: Int) {
    fun neighbours(): Set<Cell> {
        val neighbours = (-1..1).flatMap { dx ->
            (-1..1).map { dy ->
                Cell(x + dx, y + dy)
            }
        }
            .filter { it != this }
            .toSet()

        return neighbours
    }

    fun numLiveNeighbours(game: Game): Int = neighbours().filter { game.cells.contains(it) }.size

    fun isAliveInNextGeneration(game: Game): Boolean =
        if (isAlive(game)) numLiveNeighbours(game) in (2..3)
        else numLiveNeighbours(game) == 3

    private fun isAlive(game: Game) = this in game.cells
}

fun gameOf(boardString: String): Game {
    val linesOfText = boardString.trimIndent().split("\n")

    val height = linesOfText.size
    val width = linesOfText[0].length

    val cells = linesOfText.liveCells()

    return Game(cells, width, height)
}

private fun List<String>.liveCells(): Set<Cell> {
    return mapIndexed { y, line -> line.liveCells(y) }
        .flatten()
        .toSet()
}

private fun String.liveCells(y: Int): List<Cell> {
    return this.mapIndexed { x, cellChar ->
        if (cellChar == 'x') Cell(x, y)
        else null
    }.filterNotNull()
}
