package com.example.lab14

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

// 繼承 OnMapReadyCallback
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && requestCode == 0) {
            // 檢查是否所有權限都已經被允許
            val allGranted = grantResults.all {
                it == PackageManager.PERMISSION_GRANTED
            }
            if (allGranted) {
                // 重新讀取地圖
                loadMap()
            } else {
                // 結束應用程式
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 讀取地圖
        loadMap()
    }

    // 實作 onMapReady，當地圖準備好時會呼叫此方法
    override fun onMapReady(map: GoogleMap) {
        // 是否允許精確位置權限
        val isAccessFineLocationGranted =
            ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        // 是否允許粗略位置權限
        val isAccessCoarseLocationGranted =
            ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        if (isAccessFineLocationGranted && isAccessCoarseLocationGranted) {
            // 顯示目前位置與目前位置的按鈕
            map.isMyLocationEnabled = true
            // 加入標記
            val marker = MarkerOptions()
            marker.position(LatLng(25.033611, 121.565000))
            marker.title("台北101")
            marker.draggable(true)
            map.addMarker(marker)
            marker.position(LatLng(25.047924, 121.517081))
            marker.title("台北車站")
            marker.draggable(true)
            map.addMarker(marker)
            // 繪製線段
            val polylineOpt = PolylineOptions()
            polylineOpt.add(LatLng(25.033611, 121.565000))
            polylineOpt.add(LatLng(25.032435, 121.534905))
            polylineOpt.add(LatLng(25.047924, 121.517081))
            polylineOpt.color(Color.BLUE)
            val polyline = map.addPolyline(polylineOpt)
            polyline.width = 10f
            // 移動視角
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(25.035, 121.54), 13f))
        } else {
            // 請求權限
            // 精確定位包含粗略定位，因此只要求精確定位權限就好
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0
            )
        }
    }

    // 讀取地圖
    private fun loadMap() {
        // 取得 SupportMapFragment
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        // 使用非同步的方式取得地圖
        mapFragment.getMapAsync(this)
    }
}