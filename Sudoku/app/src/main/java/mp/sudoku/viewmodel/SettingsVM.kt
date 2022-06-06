package mp.sudoku.viewmodel

import android.content.Context
import mp.sudoku.model.database.sharedpreferences.SettingsSharedPreferences

class SettingsVM (context: Context) {

    private val settingsSharedPreferences = SettingsSharedPreferences(context)

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