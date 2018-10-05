package ee.applaud.test.mapapp.facebook

import com.facebook.GraphResponse


/**
 * Interface in Java style - I have heard you can put actual code inside interfaces as well but I
 * didn't have time to look into it
 */

interface FacebookCallback {

    fun onRestaurantsResponse(response: GraphResponse)
}