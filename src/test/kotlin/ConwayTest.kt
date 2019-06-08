import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class ConwayTest {

    @Test
    fun `empty board stays empty`() {
        assertThat(
            gameOf(
                """
                    ....
                    ....
                    ....
                    ....
                """
            ).tick(),
            equalTo(
                gameOf(
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
            gameOf(
                """
                    ....
                    .x..
                    ....
                    ....
                """
            ).tick(),
            equalTo(
                gameOf(
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
            gameOf(
            """
                ....
                .xx.
                .xx.
                ....
            """
            ).tick(),
            equalTo(
                gameOf(
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
            gameOf(
                """
                .....
                ..x..
                .x.x.
                ..x..
                .....
            """
            ).tick(),
            equalTo(
                gameOf(
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
            gameOf(
                """
                .....
                .....
                .xxx.
                .....
                .....
            """
            ).tick(),
            equalTo(
                gameOf(
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
}
