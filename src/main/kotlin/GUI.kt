import java.awt.Color
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel

var game = gameWithGosperGliderGun

fun main() {
    val mainWindow = MainWindow()
    EventQueue.invokeAndWait {
        mainWindow.open()
    }

    while (true) {
        Thread.sleep(50)
        game = game.tick()
        EventQueue.invokeAndWait { mainWindow.repaint() }
    }
}

class MainWindow() : JFrame() {
    var conwayCanvas: ConwayCanvas = ConwayCanvas()

    init {
        setTitle("Conway's Game Of Life, fools")
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        contentPane.add(conwayCanvas)
    }

    fun open() {
        pack()
        validate()

        setLocationRelativeTo(null)

        isVisible = true
    }
}

class ConwayCanvas() : JPanel() {

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
}
