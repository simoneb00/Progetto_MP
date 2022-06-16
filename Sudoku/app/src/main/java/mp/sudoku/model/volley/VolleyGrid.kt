package mp.sudoku.model.volley

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/*
Classe utile per la reflection di volley
 */
@Parcelize
class VolleyGrid : Parcelable {
    @IgnoredOnParcel
    var board: List<List<String>> = listOf(listOf(""))
}