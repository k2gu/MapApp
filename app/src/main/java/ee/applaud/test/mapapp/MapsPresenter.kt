package ee.applaud.test.mapapp

import com.here.android.mpa.common.GeoCoordinate
import com.here.android.mpa.common.Image
import com.here.android.mpa.mapping.MapMarker
import com.here.android.mpa.mapping.MapRoute
import ee.applaud.test.mapapp.util.StringFetcher
import ee.applaud.test.mapapp.facebook.FacebookClient
import ee.applaud.test.mapapp.maps.MapsClient
import timber.log.Timber

/**
 * This class acts like a state machine. It communicates with clients and decide what to display on view.
 * It does not know how and why it does something but it just decides what should the current state be.
 */

open class MapsPresenter(val view: MapsView, private val stringFetcher: StringFetcher) {
    private lateinit var mapsClient: MapsClient
    private lateinit var facebookClient: FacebookClient
    private var markerLocationList: List<GeoCoordinate> = ArrayList()

    fun start() {
        createClients()
        view.showLoading()
        facebookClient.requestRestaurantsData()

        if (view.hasRequiredPermissions())
            mapsClient.initMap(view.getMapFragment())
        else
            view.getPermissions()
    }

    private fun createClients() {
        mapsClient = MapsClient(this)
        facebookClient = FacebookClient(this)
    }

    fun onPermissionGranted() {
        mapsClient.initMap(view.getMapFragment())
    }

    fun onMarkersLocationsCalculated(markersLocations: GeoCoordinate) {
        markerLocationList += markersLocations
    }

    fun onRouteCalculated(mapRoute: MapRoute) {
        view.displayMap(mapRoute)
        displayMarkers()
        view.hideLoading()
    }

    private fun displayMarkers() {
        for (geoCoordinate in markerLocationList) {
            view.displayMarkerOnMap(generateMapMarker(geoCoordinate))
        }
    }

    private fun generateMapMarker(geoCoordinate: GeoCoordinate): MapMarker {
        val markerImage = Image()
        //TODO currently using totally random marker that does not fit right and respond to zooming correctly
        //TODO replace with proper one
        markerImage.setImageResource(R.drawable.location_pointer)
        return MapMarker(geoCoordinate, markerImage)
    }

    fun onError(errorType: ErrorType, reason: String) {
        when (errorType) {
            ErrorType.MAP_INIT -> Timber.e(buildErrorMessage(R.string.unable_to_init_map_error, reason))
            ErrorType.ROUTE_CALCULATION -> Timber.e(buildErrorMessage(R.string.route_calculation_error, reason))
            ErrorType.FACEBOOK -> Timber.e(buildErrorMessage(R.string.facebook_error, reason))
            //TODO network error - should handle differently? Try to reconnect - detect when network comes back
        }
        view.showGeneralError()
    }

    private fun buildErrorMessage(stringId: Int, reason: String): String {
        return String.format("%s: %s", stringFetcher.getString(stringId), reason)
    }

    fun setMapsClient(mapsClient: MapsClient) {
        this.mapsClient = mapsClient
    }

    fun setFacebookClient(facebookClient: FacebookClient) {
        this.facebookClient = facebookClient
    }

    enum class ErrorType { MAP_INIT, ROUTE_CALCULATION, FACEBOOK }

}