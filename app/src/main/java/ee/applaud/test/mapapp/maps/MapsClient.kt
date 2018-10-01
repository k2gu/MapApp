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
import ee.applaud.test.mapapp.MapsView

class MapsClient(val view: MapsView)  {

    fun initMap(mapFragment: MapFragment) {
        mapFragment.init { error ->
            if (error == OnEngineInitListener.Error.NONE) {
                val map = mapFragment.map
                map.setCenter(GeoCoordinate(58.639236, 26.133681), Map.Animation.NONE)
                calculateRoute()
            } else {
                //TODO SHOW ERROR
                println("ERROR: Cannot initialize MapFragment")
            }
        }
    }

    private fun calculateRoute() {
        val rm = RouteManager()
        val routePlan = RoutePlan()
        routePlan.addWaypoint(GeoCoordinate(59.479804, 24.748816))
        routePlan.addWaypoint(GeoCoordinate(57.963036, 27.640212))
        val routeOptions = RouteOptions()
        routeOptions.transportMode = RouteOptions.TransportMode.CAR
        routeOptions.routeType = RouteOptions.Type.FASTEST

        routePlan.routeOptions = routeOptions
        rm.calculateRoute(routePlan, RouteListener())

    }

    private inner class RouteListener : RouteManager.Listener {

        override fun onProgress(percentage: Int) {
            // Display a message indicating calculation progress
        }

        override fun onCalculateRouteFinished(error: RouteManager.Error, routeResult: List<RouteResult>) {
            if (error == RouteManager.Error.NONE) {
                val mapRoute = MapRoute(routeResult[0].route)
                view.addMapRoute(mapRoute)
            } else {
                // TODO Display a message indicating route calculation failure
            }
        }
    }
}