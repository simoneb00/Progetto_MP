package mp.sudoku.model

import mp.sudoku.viewmodel.StopWatch

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

    fun getOnlyCurrent(): Game? {
        return current
    }

    fun deleteCurrent(){
        current = null
    }

}