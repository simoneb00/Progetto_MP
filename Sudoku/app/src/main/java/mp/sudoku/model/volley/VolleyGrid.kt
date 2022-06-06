package mp.sudoku.model.volley

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class VolleyGrid : Parcelable {
    @IgnoredOnParcel
    var board: List<List<String>> = listOf(listOf(""))
}