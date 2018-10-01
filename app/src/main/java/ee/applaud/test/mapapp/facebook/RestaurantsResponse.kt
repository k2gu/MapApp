package ee.applaud.test.mapapp.facebook;

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

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
        var city: String = ""
        var country: String = ""
        var latitude: String = ""
        var longitude: String = ""
        var street: String = ""
        var zip: String = ""
    }

    class ListDeserializer : ResponseDeserializable<List<RestaurantsResponse>> {
        override fun deserialize(content: String) =
                Gson().fromJson<List<RestaurantsResponse>>(content, object : TypeToken<List<RestaurantsResponse>>() {}.type)
    }
}
