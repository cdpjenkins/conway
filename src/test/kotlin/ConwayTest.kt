import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class ConwayTest {
    @Test
    fun `everything is dead`() {
        val everythingDeadGame = Game()

        assertThat(everythingDeadGame.tick(), equalTo(everythingDeadGame))
    }

    class Game {
        fun tick(): Game = this
    }
}
