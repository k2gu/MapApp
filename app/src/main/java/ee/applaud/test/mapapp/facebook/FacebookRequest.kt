package ee.applaud.test.mapapp.facebook

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod

class FacebookRequest(private var callback: FacebookCallback) {

    fun getRestaurantsNearby(parameters: Bundle) {
        GraphRequest(AccessToken("174108643469871|lDCJn9mHPbK9mGvBkeY6JC2oFGg", "174108643469871", "kirke.pralla",
                null, null, null, null, null), "/search",
                parameters, HttpMethod.GET, GraphRequest.Callback { response ->
            if (response != null) {
                callback.onRestaurantsResponse(response)
            }
            //TODO Errorhandling
        }).executeAsync()
    }

    fun getLikes(pageId: String) {

    }
}