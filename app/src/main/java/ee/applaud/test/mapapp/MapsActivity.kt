package ee.applaud.test.mapapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.here.android.mpa.common.GeoCoordinate
import ee.applaud.test.mapapp.facebook.FacebookClient
import com.here.android.mpa.common.Image
import com.here.android.mpa.mapping.*
import ee.applaud.test.mapapp.maps.MapsClient


class MapsActivity : MapsView, AppCompatActivity() {

    private var mapFragment: MapFragment = MapFragment()
    private var permissionManager: PermissionManager = PermissionManager()
    private var facebookClient: FacebookClient = FacebookClient(this)
    private var mapClient = MapsClient(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapFragment = fragmentManager.findFragmentById(R.id.mapfragment) as MapFragment
        if (permissionManager.hasRequiredPermissions(this)) {
            mapClient.initMap(mapFragment)
            facebookClient.requestRestaurantsData()
        } else {
            permissionManager.setUpPermissions(this)
        }
        //TODO check if has permissions first and then do everything

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("tag", "Permission has been denied by user")
                } else {
                    Log.i("tag", "Permission has been granted by user")
                    mapClient.initMap(mapFragment)
                }
            }
        }
    }

    override fun addMapRoute(mapRoute: MapRoute) {
        mapFragment.map.addMapObject(mapRoute)
    }

    override fun displayMarkerOnMap(location: GeoCoordinate, markerTitle: String) {
        val markerImage = Image()
        markerImage.setImageResource(R.drawable.location_pointer)
        val mapMarker = MapMarker(location, markerImage)
        mapMarker.title = markerTitle
        //TODO check if title is ok place for displaying likes
        mapFragment.map.addMapObject(mapMarker)
    }
}