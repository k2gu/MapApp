package ee.applaud.test.mapapp

import ee.applaud.test.mapapp.facebook.FacebookClient
import ee.applaud.test.mapapp.facebook.FacebookRequest
import org.junit.Rule
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit

class FacebookClient_should {
    @Rule
    var rule = MockitoJUnit.rule()!!
    var request: FacebookRequest = Mockito.mock(FacebookRequest::class.java)
    var mapsView: MapsView = Mockito.mock(MapsView::class.java)
    private val client: FacebookClient = FacebookClient(mapsView)

    fun requests_restaurants_data_when_request_restaurants_data_is_called() {
        client.requestRestaurantsData()

        verify(request).getRestaurantsNearby(any())
    }

}