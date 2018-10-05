package ee.applaud.test.mapapp

import com.here.android.mpa.mapping.MapFragment
import com.here.android.mpa.mapping.MapRoute
import com.nhaarman.mockitokotlin2.*
import ee.applaud.test.mapapp.facebook.FacebookClient
import ee.applaud.test.mapapp.maps.MapsClient
import ee.applaud.test.mapapp.util.StringFetcher
import org.junit.Before
import org.junit.Test

/**
 * There aren't as many tests as should. I wrote missing test's names in here and in FacebookClient's
 * test file. (Even if all of those would be done, it would not be enough - for example maps client
 * should be tested as well).
 */
class MapsPresenter_should {
    private val mapsView: MapsView = mock()
    private val stringFetcher: StringFetcher = mock()
    private val mapFragment: MapFragment = mock()
    private var presenter: MapsPresenter = MapsPresenter(mapsView, stringFetcher)
    private val facebookClient: FacebookClient = mock()
    private val mapsClient: MapsClient = mock()

    @Before
    fun setUp() {
        presenter.setFacebookClient(facebookClient)
        presenter.setMapsClient(mapsClient)
    }

    @Test
    fun show_loading_on_presenter_start() {
        //nothing to prepare

        presenter.start()

        verify(mapsView).showLoading()
    }

    @Test
    fun request_facebook_data_on_app_start() {
        //TODO
    }

    @Test
    fun init_map_on_start_if_permissions_granted() {
        given(mapsView.hasRequiredPermissions()).willReturn(false)
        given(mapsView.getMapFragment()).willReturn(mapFragment)

        presenter.start()

        verify(mapsClient).initMap(mapFragment)
    }

    @Test
    fun request_permissions_if_permissions_are_not_granted() {
        given(mapsView.getMapFragment()).willReturn(mapFragment)
        given(mapsView.hasRequiredPermissions()).willReturn(false)

        presenter.start()

        verify(mapsView).getPermissions()
    }

    @Test
    fun init_map_on_permissions_granted() {
        given(mapsView.getMapFragment()).willReturn(mapFragment)

        presenter.onPermissionGranted()

        verify(mapsClient).initMap(mapFragment)
    }

    @Test
    fun add_markers_to_list_if_calculation_was_successful() {
        //TODO
    }

    @Test
    fun display_route_on_map_on_route_calculated() {
        val mapRoute = MapRoute()

        presenter.onRouteCalculated(mapRoute)

        verify(mapsView).displayMap(mapRoute)
    }

    @Test
    fun display_each_marker_separately_on_map_on_route_calculated() {
        //TODO
    }

    @Test
    fun hide_loading_on_route_calculated() {
        val mapRoute = MapRoute()

        presenter.onRouteCalculated(mapRoute)

        verify(mapsView).hideLoading()
    }
}