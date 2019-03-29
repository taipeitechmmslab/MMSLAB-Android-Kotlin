package bluenet.com.lab10

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        Toast.makeText(this, "啟動成功！", Toast.LENGTH_SHORT).show()
    }
}
