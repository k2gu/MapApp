package ee.applaud.test.mapapp.facebook

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Data model for facebook restaurants response
 */

class RestaurantsResponse {

    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    class Data {
        var id: Long = 0
        var name: String = ""
        var location: Location = Location()
    }

    class Location {
        var latitude: String = ""
        var longitude: String = ""
    }
}
