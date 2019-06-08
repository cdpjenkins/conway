import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

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

    @Test
    fun `tub stays the same`() {
        assertThat(
            boardOf(
                """
                .....
                ..x..
                .x.x.
                ..x..
                .....
            """
            ).tick(),
            equalTo(
                boardOf(
                    """
                .....
                ..x..
                .x.x.
                ..x..
                .....
            """
                )
            )
        )
    }

    @Test
    fun `blinker blinks`() {
        assertThat(
            boardOf(
                """
                .....
                .....
                .xxx.
                .....
                .....
            """
            ).tick(),
            equalTo(
                boardOf(
                    """
                .....
                ..x..
                ..x..
                ..x..
                .....
            """
                )
            )
        )
    }

    private fun boardOf(boardString: String): Game {
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
}

data class Game(val cells: Set<Cell>) {
    fun tick(): Game {
        val cellsPlusNeighbours = cells.union(cells.flatMap { it.neighbours() })
        val filter = cellsPlusNeighbours.filter { it.gonnaLive(this) }.toSet()

        return Game(filter)
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
