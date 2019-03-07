package bluenet.com.lab14

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class Data{
    lateinit var result:Result

    class Result{
        lateinit var results : Array<Results>

        class Results{
            val Station = ""
            val Destination = ""
        }
    }
}

class MainActivity : AppCompatActivity() {
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.extras?.getString("json")?.let {
                val data = Gson().fromJson(it, Data::class.java)
                val items = arrayOfNulls<String>(data.result.results.size)

                for(i in 0 until data.result.results.size)
                    items[i] = "\n列車即將進入 :${data.result.results[i].Station}" +
                            "\n列車行駛目的地 :${data.result.results[i].Destination}"

                this@MainActivity.runOnUiThread {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("台北捷運列車到站站名")
                        .setItems(items,null)
                        .show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentfilter = IntentFilter("MyMessage")
        registerReceiver(receiver, intentfilter)

        btn_query.setOnClickListener {
            val req = Request.Builder()
                .url("https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire" +
                        "&rid=55ec6d6e-dc5c-4268-a725-d04cc262172b").build()

            OkHttpClient().newCall(req).enqueue(object: Callback {
                override fun onResponse(call: Call, response: Response) {
                    response.body()?.let {
                        sendBroadcast(Intent("MyMessage").putExtra("json", it.string()))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.e("查詢失敗","$e")
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}