package bluenet.com.lab12

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val REQUEST_PERMISSIONS = 1

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.isEmpty()) return
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                for (result in grantResults)
                    if (result != PackageManager.PERMISSION_GRANTED)
                        finish()
                    else{
                        val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                        map.getMapAsync(this)
                    }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS)
        else{
            val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            map.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return

        map.isMyLocationEnabled = true

        val marker = MarkerOptions()
        marker.position(LatLng(25.033611, 121.565000))
        marker.title("台北101")
        marker.draggable(true)
        map.addMarker(marker)
        marker.position(LatLng(25.047924, 121.517081))
        marker.title("台北車站")
        marker.draggable(true)
        map.addMarker(marker)

        val polylineOpt = PolylineOptions()
        polylineOpt.add(LatLng(25.033611, 121.565000))
        polylineOpt.add(LatLng(25.032728, 121.565137))
        polylineOpt.add(LatLng(25.047924, 121.517081))
        polylineOpt.color(Color.BLUE)
        val polyline = map.addPolyline(polylineOpt)
        polyline.width = 10f

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.04, 121.54), 13f))
    }
}
