package mp.sudoku.model

import mp.sudoku.viewmodel.StopWatch

class CurrentGame {
    private var current: Game? = null
    var solution: List<List<Int>>? = null
    var timer: StopWatch? = null

    companion object {
        var mInstance: CurrentGame? = null

        @Synchronized
        fun getInstance(): CurrentGame {
            if (mInstance == null) {
                mInstance = CurrentGame()
            }
            return mInstance!!
        }
    }

    fun getCurrent(): Game? {
        if (current == null) {
            current = Game()
        }
        if(timer == null) {
            timer = StopWatch()
        }
        return current!!
    }

    fun deleteCurrent(){
        current = null
    }

}