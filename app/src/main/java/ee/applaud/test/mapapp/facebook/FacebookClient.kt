package ee.applaud.test.mapapp.facebook

import android.os.Bundle
import com.facebook.GraphResponse
import com.google.gson.Gson
import com.here.android.mpa.common.GeoCoordinate
import ee.applaud.test.mapapp.MapsView

class FacebookClient(val view: MapsView) : FacebookCallback {

    fun requestRestaurantsData() {
        val parameters = Bundle()
        parameters.putString("type", "place")
        parameters.putString("fields", "id,name,location")
        parameters.putString("q", "restaurant")
        parameters.putString("center", "58.639236,26.133681")
        parameters.putString("distance", "20000")

        val request = FacebookRequest(this)
        request.getRestaurantsNearby(parameters)
    }

    override fun onRestaurantsResponse(response: GraphResponse) {
        val jsonString = response.jsonObject.toString()
        val restaurantsResponse = Gson().fromJson(jsonString, RestaurantsResponse::class.java)
        var responseList: List<RestaurantsResponse.Data> = ArrayList()
        for (data in restaurantsResponse.data!!) {
            responseList += data
            //TODO küsi andmeid õige tüübina'
            view.displayMarkerOnMap(GeoCoordinate(data.location.latitude.toDouble(), data.location.longitude.toDouble()), data.name)
        }
    }

    override fun onPageLikesResponse(response: GraphResponse) {

    }
}