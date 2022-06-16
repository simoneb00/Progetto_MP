package mp.sudoku.model.volley

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley




/*
Classe singleton che ha la responsabilità di ottenere un coda volley, in modo da avere una sola coda per tutta la vita dell'app
 */
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