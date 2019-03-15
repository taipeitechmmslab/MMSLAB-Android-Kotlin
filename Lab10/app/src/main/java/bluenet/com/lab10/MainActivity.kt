package bluenet.com.lab10

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //啟動Service並結束MainActivity
        btn_start.setOnClickListener {
            startService(Intent(this, MyService::class.java))
            Toast.makeText(this, "啟動Service", Toast.LENGTH_SHORT)
            finish()
        }
    }
}
