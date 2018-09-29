package ee.applaud.test.mapapp

import android.support.v4.app.FragmentActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : FragmentActivity(), OnMapReadyCallback, MapsView {
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val facebookClient = FacebookClient(this)
        facebookClient.requestRestaurantsData()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val tallinn = LatLng(59.479804, 24.748816)
        val varska = LatLng(57.963036, 27.640212)
        val midPoint = LatLng(58.639236, 26.133681)
        mMap!!.addMarker(MarkerOptions().position(tallinn).title("Tallinn"))
        mMap!!.addMarker(MarkerOptions().position(varska).title("Värska"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(tallinn))
    }

    override fun displayMarkerOnMap(location: LatLng, markerTitle: String){
        mMap!!.addMarker(MarkerOptions().position(location).title(markerTitle))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(location))
    }
}
