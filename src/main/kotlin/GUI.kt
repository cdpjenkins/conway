import java.awt.Color
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel


fun main() {
    val mainWindow = MainWindow(gameWithGosperGliderGun)
    EventQueue.invokeAndWait {
        mainWindow.open()
    }

    while (true) {
        Thread.sleep(50)
        mainWindow.updateGame(mainWindow.game.tick())
    }
}

class MainWindow(game: Game) : JFrame() {
    val game get() = conwayCanvas.game

    var conwayCanvas: ConwayCanvas = ConwayCanvas(game)

    init {
        setTitle("Conway's Game Of Life, fools")
        defaultCloseOperation = EXIT_ON_CLOSE

        contentPane.add(conwayCanvas)
    }

    fun open() {
        pack()
        validate()

        setLocationRelativeTo(null)

        isVisible = true
    }

    fun updateGame(game: Game) {
        conwayCanvas.updateGame(game)
        EventQueue.invokeAndWait { this.repaint() }
    }
}

class ConwayCanvas(var game: Game) : JPanel() {

    private val cellColours = mapOf(
        true to Color.BLACK,
        false to Color.WHITE)

    override fun getPreferredSize(): Dimension {
        return Dimension(CELL_WIDTH * game.width, CELL_HEIGHT * game.height)
    }

    override fun paintComponent(g: Graphics) {
        (0..game.height).forEach { y ->
            (0..game.width).forEach { x ->
                g.drawCell(x, y)
            }
        }
    }

    private fun Graphics.drawCell(x: Int, y: Int) {
        this.color = cellColours[game.isCellAlive(x, y)]
        this.fillRect(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH - 1, CELL_HEIGHT - 1)
    }

    fun updateGame(game: Game) {
        this.game = game
    }
}
