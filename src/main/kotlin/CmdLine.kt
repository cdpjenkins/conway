fun main() {
    var board = gameWithGosperGliderGun
    while (true) {
        board.println()
        println()
        Thread.sleep(200)
        board = board.tick()
    }
}
