import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConwayTest {
    @Test
    fun `empty board stays empty`() {
        gameOf(
            """
                ....
                ....
                ....
                ....
            """
        ).tick().assert(
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
        gameOf(
            """
                ....
                .x..
                ....
                ....
            """
        ).tick().assert(
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
        gameOf(
            """
                ....
                .xx.
                .xx.
                ....
            """
        ).tick().assert(
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
        gameOf(
            """
                .....
                ..x..
                .x.x.
                ..x..
                .....
            """
        ).tick().assert(
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
        gameOf(
            """
                .....
                .....
                .xxx.
                .....
                .....
            """
        ).tick().assert(
            """
                .....
                ..x..
                ..x..
                ..x..
                .....
            """
        )
    }

    private fun Game.assert(expected: String) {
        assertEquals(expected.trimIndent(), this.toPrettyString())
    }
}
