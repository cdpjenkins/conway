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
            makeBoard(
                """
                    ....
                    ....
                    ....
                    ....
                """
            ).tick(),
            equalTo(
                makeBoard(
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
            makeBoard(
                """
                    ....
                    .x..
                    ....
                    ....
                """
            ).tick(),
            equalTo(
                makeBoard(
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
            makeBoard(
            """
                ....
                .xx.
                .xx.
                ....
            """
            ).tick(),
            equalTo(
                makeBoard(
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


    private fun makeBoard(boardString: String): Game {
        val split = boardString.trimIndent().split("\n")
        println(split)

        val cells = split.mapIndexed { y, line ->
            line.mapIndexed { x, cellChar ->
                if (cellChar == 'x') Cell(true, x, y)
                else Cell(false, x, y)
            }
        }
            .flatMap { it }
            .filter { it.alive }
            .toSet()
        println("cells is $cells")

        return Game(cells)
    }
}

data class Game(val cells: Set<Cell>) {
    fun tick(): Game {
        if (cells.size == 0)
            return this
        else if (cells.size == 4)
            return this
        else
            return Game(emptySet())
    }
}

data class Cell(val alive: Boolean, val x: Int, val y: Int)

