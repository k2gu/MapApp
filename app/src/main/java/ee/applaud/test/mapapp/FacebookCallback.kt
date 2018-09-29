package ee.applaud.test.mapapp

import com.facebook.GraphResponse

interface FacebookCallback {

    fun onRestaurantsResponse(response: GraphResponse)

    fun onPageLikesResponse(response: GraphResponse)
}