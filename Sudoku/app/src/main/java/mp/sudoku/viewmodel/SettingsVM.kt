package mp.sudoku.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import mp.sudoku.model.Game
import mp.sudoku.model.database.DBGame
import mp.sudoku.model.database.sharedpreferences.SettingsSharedPreferences
import mp.sudoku.viewmodel.repository.RepositoryGame

class SettingsVM (context: Context) {
    private val repGame: RepositoryGame
    private val settingsSharedPreferences = SettingsSharedPreferences(context)
    var allGames: LiveData<List<Game>>
    init {
        val db = DBGame.getInstance(context as Application) //Singleton instance of the db
        val daoGame = db.gameDAO()

        repGame = RepositoryGame(daoGame)

        allGames = repGame.getAllGames()
    }

    fun updateShowTimerSetting(value: Boolean) {
        settingsSharedPreferences.updateShowTimerSetting(value)
    }

    fun updateShowScoreSetting(value: Boolean) {
        settingsSharedPreferences.updateShowScoreSetting(value)

    }

    fun updateEnableHintsSetting(value: Boolean) {
        settingsSharedPreferences.updateEnableHintsSetting(value)
    }

    fun updateEnableDarkThemeSetting(value: Boolean) {
        settingsSharedPreferences.updateEnableDarkThemeSetting(value)
    }

    fun getTimerSetting(): Boolean {
        return settingsSharedPreferences.getTimerSetting()
    }

    fun getScoreSetting(): Boolean {
        return settingsSharedPreferences.getScoreSetting()
    }

    fun getHintsSetting(): Boolean {
        return settingsSharedPreferences.getHintsSetting()
    }

    fun getDarkModeSetting(): Boolean {
        return settingsSharedPreferences.getDarkModeSetting()
    }
}