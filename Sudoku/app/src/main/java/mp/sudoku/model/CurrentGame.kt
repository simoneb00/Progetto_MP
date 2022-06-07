package mp.sudoku.model

import com.android.volley.toolbox.Volley

class CurrentGame {
    private var current: Game? = null
    var solution: List<List<Int>>? = null

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
        return current!!
    }

    fun deleteCurrent(){
        current = null
    }

}