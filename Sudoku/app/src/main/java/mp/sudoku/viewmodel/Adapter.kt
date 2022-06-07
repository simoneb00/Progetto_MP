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

        /*
        *   the board in persistence will be saved in the following format:
        *   *1.2.3.4.5.6.7.8.9*1.2.3.4.5.6.7.8.9*... (i.e. each row is wrapped into * and each value is separated by dots)
        *
        *   this function converts the persistence format to a List<List<String>>
        *
         */
        fun boardPersistenceFormatToList(board: String): List<List<String>> {

            /*
             *  split method will return a list containing 11 rows:
             *  [[], [1.2.3.4.5.6.7.8.9], ..., [1.2.3.4.5.6.7.8.9], []]
             *  we need to remove the empty list ( indexes 0 and (rows.size -1) )
             *
             */
            val rows: MutableList<String> = board.split("*") as MutableList<String>

            rows.removeAt(0)
            rows.removeAt(rows.size - 1)

            var values: MutableList<List<String>> = mutableListOf()     // this creates an empty mutableList

            rows.forEach { row ->
                val rowValues: List<String> = row.split(".")      // we get a list of strings (every value in the row)
                values.add(rowValues)
            }

            return values
        }


        /* this function converts a list of lists of integers in the persistence format (explained above) */
        fun boardListToPersistenceFormat(board: List<List<Int>>): String {

            var boardString = ""

            board.forEach { intList ->
                boardString = "$boardString*"
                intList.forEach { int ->
                    boardString += if (intList.indexOf(int) == intList.size - 1)
                        int.toString()
                    else
                        "$int."
                }
            }

            boardString += "*"

            return boardString
        }
    }

}