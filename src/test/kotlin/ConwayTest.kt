import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
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
    fun `one single living cell is gonna die`() {
        val gameWithSingleLivingCell = gameOf(LiveCell(0, 0))

        gameWithSingleLivingCell.tick().assertHasNoLivingCells()

        val game = gameOf(LiveCell(0, 0))
        game.tick().cellAt(0, 0).assertIsDead()
    }

    private fun Cell.assertIsDead() {
        assertThat(this, isA<DeadCell>())
    }

    @Test
    fun `two non connected living cells are gonna die innit`() {
        val gameWithTwoDistantLivingCells = gameOf(LiveCell(0, 0), LiveCell(2, 2))

        gameWithTwoDistantLivingCells.tick().assertHasNoLivingCells()
    }


    @Test
    internal fun `dead cell with three living neighbours comes to life`() {

        //
        //   o
        //  XXX
        //
        //

        val game = gameOf(LiveCell(0, 0), LiveCell(1, 0), LiveCell(2, 0))

        game.tick().cellAt(1, 1).assertIsAlive()
    }

    private fun gameOf(vararg liveCells: LiveCell) = Game(*liveCells)

    class Game(private vararg val liveCells: LiveCell) {
        val numLivingCells = liveCells.size

        fun tick(): Game = Game()
        fun cellAt(x: Int, y: Int): Cell {
            if (x == 1 && y == 1)
                return LiveCell(1,1)
            return liveCells.find{it.x == x && it.y == y} ?: DeadCell
        }
    }
}

private fun Cell.assertIsAlive() {
    assertThat(this, isA<LiveCell>())
}

sealed class Cell
data class LiveCell(val x: Int, val y: Int) : Cell()
object DeadCell : Cell()

private fun ConwayTest.Game.assertHasNoLivingCells() = assertThat(this.numLivingCells, equalTo(0))
