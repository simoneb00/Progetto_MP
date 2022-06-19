package mp.sudoku.model

/*
Classe singleton che ha la responsabilità di ottenere una sola instanza relativa al game corrente
in modo da tener traccia di quale è la partita osservata in un determinato momento di vita dell'app
 */
class CurrentGame {
    var current: Game? = null
    var solution: List<List<Int>>? = null
    var timer: StopWatch = StopWatch()


    companion object {
        private var mInstance: CurrentGame? = null

        @Synchronized
        fun getInstance(): CurrentGame {
            if (mInstance == null) {
                mInstance = CurrentGame()
            }
            return mInstance!!
        }
    }

    @JvmName("getCurrent1")
    fun getCurrent(): Game? {
        if (current == null) {
            current = Game()
        }
        return current!!
    }

}