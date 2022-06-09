package mp.sudoku.model.volley

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley





class VolleySingleton {

    private var mRequestQueue: RequestQueue? = null

    companion object {
        private var mInstance: VolleySingleton? = null

        @Synchronized
        fun getInstance(): VolleySingleton {
            if (mInstance == null) {
                mInstance = VolleySingleton()
            }
            return mInstance!!
        }
    }

    fun getRequestQueue(context: Context): RequestQueue? {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context)
        }
        return mRequestQueue
    }
}