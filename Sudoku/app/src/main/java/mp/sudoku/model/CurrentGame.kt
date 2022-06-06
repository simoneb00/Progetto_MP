package mp.sudoku.model

class CurrentGame {
    private var current: Game? = null
    private var solution: List<List<Int>>? = null

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
        return current
    }

    fun deleteCurrent(){
        current = null
    }
}