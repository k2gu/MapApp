package ee.applaud.test.mapapp.facebook

import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import ee.applaud.test.mapapp.BuildConfig
import ee.applaud.test.mapapp.util.ParameterCreator

/**
 * This class is responsible for requesting all the data needed from Facebook. Currently theres only
 * restaurants request and Im pretty sure its not how it should be done. It looks like a quick hack
 * to get things working. I did google facebook requests a bit and it seems like I need approval from
 * facebook to get it properly working and to get likes data. Due to lack of time, I did not do that.
 *
 * This class only sends out requests. Responses are going to a class that implements Facebook callbacks.
 * Currently, FacebookClient class is responsible for it.
 * Api keys are held in a separate file. They are read in as build variables and are not
 * accessible in github (currently they are because I held them as strings in the beginning but
 * I think it doesn't really matter at this point for this test task).
 */
open class FacebookRequest(private var callback: FacebookCallback) {

    fun getRestaurantsNearby() {
        GraphRequest(AccessToken(BuildConfig.facebookApiKey, BuildConfig.facebookApplicationId, BuildConfig.facebookUserId,
                null, null, null, null, null), "/search",
                ParameterCreator().getParameters(), HttpMethod.GET, GraphRequest.Callback { response ->
            if (response != null) {
                callback.onRestaurantsResponse(response)
            }
        }).executeAsync()
    }
}