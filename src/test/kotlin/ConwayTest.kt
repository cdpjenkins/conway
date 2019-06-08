import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test


// The rules
// live cell:
// 0, 1 live neighbours --> gonna die due to loneliness
// 2, 3 neighbours ---> gonna survive
// 4, 5, 6, 7, 8 neighbours ----> gonna die due to overcrowding
//

// dead cell:
// 3 neighbours ---> come to life
// else ----> meh, stay dead

//    XX
//    XX

class ConwayTest {

    @Test
    fun `empty board stays empty`() {
        assertThat(
            boardOf(
                """
                    ....
                    ....
                    ....
                    ....
                """
            ).tick(),
            equalTo(
                boardOf(
                """
                    ....
                    ....
                    ....
                    ....
                """
                )
            )
        )
    }

    @Test
    fun `lone cell dies`() {
        assertThat(
            boardOf(
                """
                    ....
                    .x..
                    ....
                    ....
                """
            ).tick(),
            equalTo(
                boardOf(
                """
                    ....
                    ....
                    ....
                    ....
                """
                )
            )
        )
    }

    @Test
    fun `block stays the same`() {
        assertThat(
            boardOf(
            """
                ....
                .xx.
                .xx.
                ....
            """
            ).tick(),
            equalTo(
                boardOf(
                """
                    ....
                    .xx.
                    .xx.
                    ....
                """
                )
            )
        )
    }

//    @Test
//    fun `tub stays the same`() {
//        assertThat(
//            boardOf(
//                """
//                .....
//                ..x..
//                .x.x.
//                ..x..
//                .....
//            """
//            ).tick(),
//            equalTo(
//                boardOf(
//                    """
//                .....
//                ..x..
//                .x.x.
//                ..x..
//                .....
//            """
//                )
//            )
//        )
//    }

    private fun boardOf(boardString: String): Game {
        val split = boardString.trimIndent().split("\n")
        println(split)

        val cells = split.mapIndexed { y, line ->
            line.mapIndexed { x, cellChar ->
                if (cellChar == 'x') Cell(x, y)
                else null
            }.filterNotNull()
        }
            .flatMap { it }
            .toSet()
        println("cells is $cells")

        return Game(cells)
    }
}

data class Game(val cells: Set<Cell>) {
    fun tick(): Game {
        val cellsWith3Neighbours = cells.filter { it.numNeighbours(this) == 3 }.toSet()
        return Game(cellsWith3Neighbours)
    }
}

data class Cell(val x: Int, val y: Int) {
    fun neighbours(): Set<Cell> {
        val thingie = (-1..1).flatMap { dx ->
            (-1..1).map { dy ->
                Cell(x + dx, y + dy)
            }
        }
            .filter { it != this }
            .toSet()
        println("thingie: $thingie")

        return thingie
    }

    fun numNeighbours(game: Game): Int = neighbours().filter { game.cells.contains(it) }.size
}
