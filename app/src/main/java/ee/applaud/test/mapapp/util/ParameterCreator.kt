package ee.applaud.test.mapapp.util

import android.os.Bundle

open class ParameterCreator {

    fun getParameters(): Bundle {
        val parameters = Bundle()
        parameters.putString("type", "place")
        parameters.putString("fields", "id,name,location")
        parameters.putString("q", "restaurant")
        parameters.putString("center", "58.639236,26.133681")
        parameters.putString("distance", "20000")
        parameters.putString("limit", "10")
        return parameters
    }
}