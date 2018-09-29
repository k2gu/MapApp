package ee.applaud.test.mapapp

import com.google.android.gms.maps.model.LatLng

interface MapsView {
    fun displayMarkerOnMap(location: LatLng, markerTitle: String)
}