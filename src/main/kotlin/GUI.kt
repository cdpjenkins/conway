import java.awt.Color
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel

var game = gameWithGosperGliderGun

fun main() {

    var mainWindow: MainWindow? = null
    EventQueue.invokeAndWait{
        mainWindow = MainWindow()
        mainWindow?.isVisible = true
    }

    while (true) {
        Thread.sleep(50)
        game = game.tick()
        EventQueue.invokeAndWait {
            mainWindow?.repaint()
        }
    }
}

class MainWindow() : JFrame() {
    var conwayCanvas: ConwayCanvas = ConwayCanvas()

    init {
        setTitle("Conway's Game Of Life, suckaz")
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        contentPane.add(conwayCanvas)

        pack()
        validate()

        setLocationRelativeTo(null)
    }
}

class ConwayCanvas() : JPanel() {
    override fun getPreferredSize(): Dimension {
        return Dimension(CELL_WIDTH * game.width, CELL_HEIGHT * game.height)
    }

    override fun paintComponent(g: Graphics?) {
        for (y in 0..game.height) {
            for (x in 0..game.width) {
                val cell = game.liveCellAt(x, y)
                if (cell) {
                    g?.color = Color.BLACK
                } else {
                    g?.color = Color.WHITE
                }
                g?.fillRect(x*CELL_WIDTH, y*CELL_HEIGHT, CELL_WIDTH-1, CELL_HEIGHT-1)
            }
        }
    }
}