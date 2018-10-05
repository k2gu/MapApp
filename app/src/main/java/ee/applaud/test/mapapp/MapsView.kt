package ee.applaud.test.mapapp

import com.here.android.mpa.mapping.MapFragment
import com.here.android.mpa.mapping.MapMarker
import com.here.android.mpa.mapping.MapRoute

interface MapsView {
    fun displayMarkerOnMap(mapMarker: MapMarker)

    fun displayMap(mapRoute: MapRoute)

    fun hasRequiredPermissions(): Boolean

    fun getMapFragment(): MapFragment

    fun getPermissions()

    fun showLoading()

    fun hideLoading()

    fun showGeneralError()
}