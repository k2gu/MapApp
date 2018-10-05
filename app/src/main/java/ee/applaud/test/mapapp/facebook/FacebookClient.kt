package ee.applaud.test.mapapp.facebook

import com.facebook.GraphResponse
import com.google.gson.Gson
import com.here.android.mpa.common.GeoCoordinate
import ee.applaud.test.mapapp.MapsPresenter

/**
 * This class is responsible for getting data from facebook and process the response.
 * It communicates with presenter and is the callback class for facebook responses.
 */
open class FacebookClient(private val presenter: MapsPresenter) : FacebookCallback {

    fun requestRestaurantsData() {
        val request = FacebookRequest(this)
        request.getRestaurantsNearby()
    }

    //TODO this method has too many responsibilities
    override fun onRestaurantsResponse(response: GraphResponse) {
        if (response.error == null && response.connection.responseCode == 200) {
            val jsonString = response.jsonObject.toString()
            val restaurantsResponse = Gson().fromJson(jsonString,
                    RestaurantsResponse::class.java)
            var responseList: List<RestaurantsResponse.Data> = ArrayList()
            for (data in restaurantsResponse.data!!) {
                responseList += data
                //TODO create model so that it would be possible to get latitude and longitude as doubles by default
                presenter.onMarkersLocationsCalculated(GeoCoordinate(data.location.latitude.toDouble(),
                        data.location.longitude.toDouble()))
            }
        } else {
            presenter.onError(MapsPresenter.ErrorType.FACEBOOK, response.error.errorMessage)
        }
    }
}