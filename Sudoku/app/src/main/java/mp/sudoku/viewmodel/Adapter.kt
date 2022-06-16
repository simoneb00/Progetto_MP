package mp.sudoku.viewmodel

import mp.sudoku.model.SudokuCell

@Suppress("UNCHECKED_CAST")
class Adapter {
    companion object {
        fun changeStringToInt(board: List<List<String>>): List<List<Int>> {
            val newList: MutableList<List<Int>> = mutableListOf()

            try {
                for ((count, i) in board.withIndex()) {
                    var tempList: List<Int> = mutableListOf()
                    for (j in i.indices) {
                        if (board[count][j] != "")
                            tempList.plus(board[count][j].toInt()).also { tempList = it }
                    }
                    newList.addAll(listOf(tempList))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return newList
        }

        fun intListToStringList(intList: List<List<Int>>): List<List<String>> {
            println(
                "*************************************************" +
                        "Adapter" +
                        "*************************************************"
            )

            println("int list: " + intList)

            val newList: MutableList<List<String>> = mutableListOf()

            try {
                for ((count, i) in intList.withIndex()) {
                    var tempList: List<String> = mutableListOf()
                    for (j in i.indices) {
                        if (intList[count][j] != 0)
                            tempList.plus(intList[count][j].toString()).also { tempList = it }
                    }
                    newList.addAll(listOf(tempList))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            println("string list: " + newList)

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

            if (rows.size > 0 && rows[0] == "") {
                rows.removeAt(0)
            }
            if (rows.size > 0 && rows[rows.size - 1] == "") {
                rows.removeAt(rows.size - 1)
            }

            val values: MutableList<MutableList<String>> =
                mutableListOf()     // this creates an empty mutableList

            if (board != "empty") {

                try {
                    rows.forEach { row ->
                        val rowValues: MutableList<String> =
                            row.split(".")
                                .toMutableList()      // we get a list of strings (every value in the row)
                        values.add(rowValues)
                        if (rowValues.size > 9)
                            rowValues.removeAt(rowValues.size - 1)
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            return values
        }


        /* this function converts a list of lists of integers in the persistence format (explained above) */
        fun boardListToPersistenceFormat(board: List<List<Int>>): String {

            var boardString = ""

            board.forEach { intList ->
                boardString = "$boardString*"   // inserts a '*' after every row
                intList.forEach { int ->
                    boardString += if (intList.indexOf(int) == intList.size - 1)    // if the value is some row's last value, there will not be a dot after it
                        int.toString()
                    else
                        "$int."
                }
            }

            boardString += "*"      // inserts the final '*'

            return boardString
        }

        fun hashMapToList(hashMap: HashMap<Int, SudokuCell>): List<List<Int>> {
            val list: MutableList<MutableList<Int?>> = mutableListOf(
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
            )

            for (i in 0..8) {
                for (j in 0..8) {
                    val value = hashMap[i * 10 + j]?.value
                    list[j].add(index = i, element = value)
                }
            }

            return list as List<List<Int>>
        }
    }

}