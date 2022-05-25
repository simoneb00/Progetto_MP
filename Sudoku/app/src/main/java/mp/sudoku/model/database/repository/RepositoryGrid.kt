package mp.sudoku.model.database.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mp.sudoku.model.SudokuGrid
import mp.sudoku.model.database.dao.DAOGrid

class RepositoryGrid (private val daoGrid: DAOGrid){

    fun insertGrid(grid: SudokuGrid){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                daoGrid.insertOne(grid)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getAllGrids(){
        CoroutineScope(Dispatchers.IO).launch {
            val grids:List<SudokuGrid>
            try {
                grids = daoGrid.getAllGrids()
                for(i in grids){
                    System.out.println("Game:" + i.grid)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}