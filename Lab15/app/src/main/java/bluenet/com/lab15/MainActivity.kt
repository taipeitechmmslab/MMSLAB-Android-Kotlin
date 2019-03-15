package bluenet.com.lab15

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //本範例尚未連動Firebase，因此會出現google-services.json is missing的編譯錯誤
        //請依照書中指示『連動 Firebase Cloud Messaging』即可
    }
}
