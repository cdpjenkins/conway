import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConwayTest {
    @Test
    fun `empty board stays empty`() {
        givenAGameStartingAt(
            """
                ....
                ....
                ....
                ....
            """
        ).thenTheNextStateIs(
            """
                ....
                ....
                ....
                ....
             """
        )
    }

    @Test
    fun `lone cell dies`() {
        givenAGameStartingAt(
            """
                ....
                .x..
                ....
                ....
            """
        ).thenTheNextStateIs(
            """
                ....
                ....
                ....
                ....
            """
        )
    }

    @Test
    fun `block stays the same`() {
        givenAGameStartingAt(
            """
                ....
                .xx.
                .xx.
                ....
            """
        ).thenTheNextStateIs(
            """
                ....
                .xx.
                .xx.
                ....
            """
        )
    }

    @Test
    fun `tub stays the same`() {
        givenAGameStartingAt(
            """
                .....
                ..x..
                .x.x.
                ..x..
                .....
            """
        ).thenTheNextStateIs(
            """
                .....
                ..x..
                .x.x.
                ..x..
                .....
            """
        )
    }

    @Test
    fun `blinker blinks`() {
        givenAGameStartingAt(
            """
                .....
                .....
                .xxx.
                .....
                .....
            """
        ).thenTheNextStateIs(
            """
                .....
                ..x..
                ..x..
                ..x..
                .....
            """
        )
    }

    private fun Game.thenTheNextStateIs(nextStateInnit: String) {
        this.tick().assert(nextStateInnit)
    }

    private fun givenAGameStartingAt(startingAt: String): Game {
        return gameOf(
            startingAt
        )
    }

    private fun Game.assert(expected: String) {
        assertEquals(expected.trimIndent(), this.toPrettyString())
    }
}
