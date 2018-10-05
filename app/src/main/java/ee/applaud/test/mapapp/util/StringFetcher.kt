package ee.applaud.test.mapapp.util

import android.content.Context

/**
 * The purpose of this class is to get rid of Android dependencies in presenter and client classes
 * so that they would be easily testable
 */
open class StringFetcher(private val context: Context) {

    fun getString(stringId: Int): String {
        return context.getString(stringId)
    }

}