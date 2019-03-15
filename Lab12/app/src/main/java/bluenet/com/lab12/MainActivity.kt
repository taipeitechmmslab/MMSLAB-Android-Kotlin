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
    companion object {
        private const val REQUEST_PERMISSIONS = 1
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.isEmpty()) return
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                for (result in grantResults)
                    //若使用者拒絕給予權限則關閉APP
                    if (result != PackageManager.PERMISSION_GRANTED)
                        finish()
                    else{
                        //連接MapFragment物件
                        val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                        map.getMapAsync(this)
                    }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TODO("將AndroidManifest的YOUR_API_KEY換成自行申請的金鑰後註解此段程式")
        //檢查使用者是否已授權定位權限，向使用者要求權限
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS)
        else{
            //連接MapFragment物件
            val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            map.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        //檢查使用者是否已授權定位權限
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return
        //顯示目前位置與定位的按鈕
        map.isMyLocationEnabled = true
        //建立MarkerOptions物件
        val marker = MarkerOptions()
        //設定Marker座標
        marker.position(LatLng(25.033611, 121.565000))
        //設定Marker標題
        marker.title("台北101")
        marker.draggable(true)
        //將Marker加入Map並顯示
        map.addMarker(marker)
        marker.position(LatLng(25.047924, 121.517081))
        marker.title("台北車站")
        marker.draggable(true)
        map.addMarker(marker)
        //建立PolylineOptions物件
        val polylineOpt = PolylineOptions()
        //加入座標到PolylineOptions
        polylineOpt.add(LatLng(25.033611, 121.565000))
        polylineOpt.add(LatLng(25.032728, 121.565137))
        polylineOpt.add(LatLng(25.047924, 121.517081))
        //設定PolylineOptions顏色
        polylineOpt.color(Color.BLUE)
        //將PolylineOptions加入Map並顯示
        val polyline = map.addPolyline(polylineOpt)
        //設定Polyline寬度
        polyline.width = 10f
        //移動鏡頭（畫面）到指定座標與深度
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.04, 121.54), 13f))
    }
}
