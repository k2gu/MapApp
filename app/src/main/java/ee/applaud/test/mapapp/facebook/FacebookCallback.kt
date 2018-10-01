package ee.applaud.test.mapapp.facebook

import com.facebook.GraphResponse

interface FacebookCallback {

    fun onRestaurantsResponse(response: GraphResponse)

    fun onPageLikesResponse(response: GraphResponse)
}