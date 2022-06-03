package mp.sudoku.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.compose.ui.platform.LocalContext

class SettingsVM (context: Context) {

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    private val editor: Editor = sharedPrefs.edit()

    fun updateShowTimerSetting(value: Boolean) {
        editor.putBoolean("show_timer", value)
        editor.apply()
    }

    fun updateShowScoreSetting(value: Boolean) {
        editor.putBoolean("show_score", value)
        editor.apply()

    }

    fun updateEnableHintsSetting(value: Boolean) {
        editor.putBoolean("enable_hints", value)
        editor.apply()
    }

    fun updateEnableDarkThemeSetting(value: Boolean) {
        editor.putBoolean("enable_dark_mode", value)
        editor.apply()
    }

    fun getTimerSetting(): Boolean {
        return sharedPrefs.getBoolean("show_timer", true)
    }

    fun getScoreSetting(): Boolean {
        return sharedPrefs.getBoolean("show_score", true)
    }

    fun getHintsSetting(): Boolean {
        return sharedPrefs.getBoolean("enable_hints", true)
    }

    fun getDarkModeSetting(): Boolean {
        return sharedPrefs.getBoolean("enable_dark_mode", false)
    }
}