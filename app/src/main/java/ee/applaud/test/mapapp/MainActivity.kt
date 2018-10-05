package ee.applaud.test.mapapp

import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.here.android.mpa.mapping.*
import ee.applaud.test.mapapp.util.StringFetcher
import timber.log.Timber

/**
 * This class is responsible of the UI. It has a bit too many responsibilities. It could be improved by using fragments.
 * Most of the methods here are implementations of MapsView interface that is called by a presenter.
 * Presenter communicates with this class only though interface.
 *
 * For error handling, I currently used builtin alert dialog. I used it because its simplicity.
 * Error handling is currently not as good as it should. It doesn't recover well and the dialog is
 * closeable by just clicking anywhere.
 */

//TODO create separate fragments for maps screen, loading screen and error screen in order to remove responsibilities from this class and increase readability and maintainability

class MainActivity : MapsView, AppCompatActivity() {
    private var mapFragment: MapFragment = MapFragment()
    private var permissionManager: PermissionManager = PermissionManager()
    private var presenter: MapsPresenter? = null
    private var dialog: Dialog? = null
    private val requestCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapFragment = fragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        presenter = MapsPresenter(this, StringFetcher(baseContext))
        presenter!!.start()
    }

    //TODO replace builtin dialogs with custom design + dont allow user to close dialogs by just pressing anywhere
    override fun showLoading() {
        dialog = Dialog(this)
        dialog!!.setContentView(R.layout.progress_bar)
        dialog!!.show()
    }

    override fun hideLoading() {
        dialog!!.dismiss()
    }

    override fun showGeneralError() {
        showCustomError(getString(R.string.general_error_message))
    }

    private fun showCustomError(customMessage: String) {
        hideLoading()
        //TODO  dont allow user to close dialogs by just pressing anywhere + see if full screen error would be a better solution
        val dialog: AlertDialog = getErrorMessageBuilder(customMessage).create()
        dialog.show()
    }

    private fun getErrorMessageBuilder(customMessage: String): AlertDialog.Builder {
        val builder = AlertDialog.Builder(this)
        return builder.setMessage(customMessage)
                .setNeutralButton(getString(R.string.try_again_button_label), getDialogListener())
    }

    private fun getDialogListener(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { alertDialog, _ ->
            alertDialog.dismiss()
            //TODO improve recovery - currently try again is not working
            presenter!!.start()
        }
    }

    override fun hasRequiredPermissions(): Boolean {
        return permissionManager.hasRequiredPermissions(this)
    }

    override fun getMapFragment(): MapFragment {
        return mapFragment
    }

    override fun getPermissions() {
        permissionManager.setUpPermissions(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            this.requestCode -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Timber.e(getString(R.string.permissions_not_granted_error))
                    showCustomError(getString(R.string.permission_not_granted_error))
                } else {
                    presenter!!.onPermissionGranted()
                }
            }
        }
    }

    override fun displayMap(mapRoute: MapRoute) {
        mapFragment.map.addMapObject(mapRoute)
    }

    override fun displayMarkerOnMap(mapMarker: MapMarker) {
        mapFragment.map.addMapObject(mapMarker)
    }
}