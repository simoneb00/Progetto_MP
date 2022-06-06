package mp.sudoku.viewmodel

class Adapter {
    companion object{
        fun changeStringToInt(board:List<List<String>>): List<List<Int>> {
            var newList:MutableList<List<Int>> = mutableListOf()

            for((count, i) in board.withIndex()){
                var tempList:List<Int> = mutableListOf()
                for(j in i.indices){
                    tempList.plus(board[count][j].toInt()).also { tempList = it }
                }
                newList.addAll(listOf(tempList))
            }
            return newList
        }
    }
}