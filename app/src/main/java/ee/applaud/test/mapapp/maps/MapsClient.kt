package ee.applaud.test.mapapp.maps

import com.here.android.mpa.common.GeoCoordinate
import com.here.android.mpa.common.OnEngineInitListener
import com.here.android.mpa.mapping.Map
import com.here.android.mpa.mapping.MapFragment
import com.here.android.mpa.mapping.MapRoute
import com.here.android.mpa.routing.RouteManager
import com.here.android.mpa.routing.RouteOptions
import com.here.android.mpa.routing.RoutePlan
import com.here.android.mpa.routing.RouteResult
import ee.applaud.test.mapapp.MapsPresenter

/**
 * This class is responsible for communication with maps server. This class does not communicate
 * directly with view. It gives state updates to presenter and it decides what to do. This simplifies
 * having classes with single-responsibility.
 *
 * In current solution, I hardcoded Tallinn, Värska and midpoint coordinates in order to make as simple
 * solution as possible. If other places would be needed, it would be easy to add one request that
 * asks for coordinates.
 */

open class MapsClient(val presenter: MapsPresenter)  {
    private val centerPoint: GeoCoordinate = GeoCoordinate(58.639236, 26.133681)
    private val tallinnLocation: GeoCoordinate = GeoCoordinate(59.479804, 24.748816)
    private val varskaLocation: GeoCoordinate = GeoCoordinate(57.963036, 27.640212)

    fun initMap(mapFragment: MapFragment) {
        mapFragment.init { error ->
            if (error == OnEngineInitListener.Error.NONE) {
                displayMap(mapFragment)
                calculateRoute()
            } else {
                presenter.onError(MapsPresenter.ErrorType.MAP_INIT, error.details)
            }
        }
    }

    private fun displayMap(mapFragment: MapFragment) {
        val map = mapFragment.map
        //TODO adjust zoom level to what it should be. Currently zoomed right in the middle. Verify if this is expected behaviour
        map.zoomLevel = 10.0
        map.setCenter(centerPoint, Map.Animation.NONE)
    }

    private fun calculateRoute() {
        val rm = RouteManager()
        val routePlan = RoutePlan()
        routePlan.addWaypoint(tallinnLocation)
        routePlan.addWaypoint(varskaLocation)

        routePlan.routeOptions = getRouteOptions()
        rm.calculateRoute(routePlan, RouteListener())
    }

    private fun getRouteOptions() : RouteOptions {
        val routeOptions = RouteOptions()
        routeOptions.transportMode = RouteOptions.TransportMode.CAR
        routeOptions.routeType = RouteOptions.Type.FASTEST
        return routeOptions
    }

    //TODO handle icons on click events

    private inner class RouteListener : RouteManager.Listener {

        override fun onProgress(percentage: Int) {
        }

        override fun onCalculateRouteFinished(error: RouteManager.Error, routeResult: List<RouteResult>) {
            if (error == RouteManager.Error.NONE) {
                val mapRoute = MapRoute(routeResult[0].route)
                presenter.onRouteCalculated(mapRoute)
            } else {
                presenter.onError(MapsPresenter.ErrorType.ROUTE_CALCULATION, error.name)
            }
        }
    }
}