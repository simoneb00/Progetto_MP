package mp.sudoku.viewmodel.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mp.sudoku.model.Note
import mp.sudoku.model.database.dao.DAONote

class RepositoryNote (private val daoNote: DAONote){

    fun insertNote(note: Note){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                daoNote.insertOne(note)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getAllNotes(){
        CoroutineScope(Dispatchers.IO).launch {
            val notes:List<Note>
            try {
                notes = daoNote.getAllNotes()
                for(i in notes){
                    System.out.println("Game:" + i.description)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}