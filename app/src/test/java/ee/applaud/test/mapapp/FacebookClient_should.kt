package ee.applaud.test.mapapp

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import ee.applaud.test.mapapp.facebook.FacebookClient
import ee.applaud.test.mapapp.facebook.FacebookRequest
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit

class FacebookClient_should {
    @get:Rule
    var rule = MockitoJUnit.rule()
    var request: FacebookRequest = mock()
    var presenter: MapsPresenter = mock()
    private val client: FacebookClient = FacebookClient(presenter)

    @Test
    fun requests_restaurants_data_when_request_restaurants_data_is_called() {
        //TODO
    }

    fun generate_list_of_restaurants_on_successful_restaurants_response() {
        //TODO
    }

    fun show_error_on_unsuccessful_restaurants_response() {
        //TODO
    }
}