


fun main() {
    var board = gameOf(
        """
                ..x......
                ...x.....
                .xxx.....
                .........
                .........
                .........
                .........
                .........
                .........
            """
    )

    while (true) {
        board.printMeDo()
        println()
        Thread.sleep(500);
        board = board.tick()

    }
}

data class Game(val cells: Set<Cell>) {
    fun tick(): Game {
        val cellsPlusNeighbours = cells.union(cells.flatMap { it.neighbours() })
        val filter = cellsPlusNeighbours.filter { it.gonnaLive(this) }.toSet()

        return Game(filter)
    }

    fun printMeDo() {
        for (y in 0..20) {
            for (x in 0..20) {
                if (Cell(x, y) in cells) {
                    print("x")
                } else {
                    print(".")
                }
            }
            println()
        }
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

    fun gonnaLive(game: Game): Boolean =
        if (this in game.cells) numLiveNeighbours(game) in (2..3)
        else numLiveNeighbours(game) == 3
}

fun gameOf(boardString: String): Game {
    val split = boardString.trimIndent().split("\n")

    val cells = split.mapIndexed { y, line ->
        line.mapIndexed { x, cellChar ->
            if (cellChar == 'x') Cell(x, y)
            else null
        }.filterNotNull()
    }
        .flatMap { it }
        .toSet()

    return Game(cells)
}
