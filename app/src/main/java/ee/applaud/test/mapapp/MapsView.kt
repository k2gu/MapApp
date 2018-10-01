package ee.applaud.test.mapapp

import com.here.android.mpa.common.GeoCoordinate
import com.here.android.mpa.mapping.MapRoute

interface MapsView {
    fun displayMarkerOnMap(location: GeoCoordinate, markerTitle: String)

    fun addMapRoute(mapRoute: MapRoute)
}