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

class MainActivity : AppCompatActivity() {
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //判斷回傳結果是否為空
            val json = intent.extras?.getString("json")?: return
            //解析Intent取得JSON字串，把json物件以Data格式做轉換
            val data = Gson().fromJson(json, Data::class.java)
            val items = arrayOfNulls<String>(data.result.results.size)
            //建立一個字串陣列，用於提取『站名』與『目的地』資訊
            for(i in 0 until data.result.results.size)
                items[i] = "\n列車即將進入 :${data.result.results[i].Station}" +
                        "\n列車行駛目的地 :${data.result.results[i].Destination}"
            //使用者介面的操作必須在UI Thread上執行
            this@MainActivity.runOnUiThread {
                //使用Dialog呈現結果
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("台北捷運列車到站站名")
                    .setItems(items) { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                    .show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //註冊Receiver，用來接收Http Response
        registerReceiver(receiver, IntentFilter("MyMessage"))

        btn_query.setOnClickListener {
            //建立一個Request物件，並使用url()方法加入URL
            val req = Request.Builder()
                .url("https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire" +
                        "&rid=55ec6d6e-dc5c-4268-a725-d04cc262172b").build()
            //建立okHttpClient物件，newCall()送出請求，enqueue()接收回傳
            OkHttpClient().newCall(req).enqueue(object: Callback {
                //發送成功執行此方法
                override fun onResponse(call: Call, response: Response) {
                    //判斷伺服器回傳狀態
                    when{
                        response.code()==200 ->{
                            //判斷回傳是否為空
                            val json = response.body()?.string()?:return
                            //取得用response的回傳結果（Json字串），並使用廣播發送
                            sendBroadcast(Intent("MyMessage").putExtra("json", json))
                        }
                        !response.isSuccessful ->Log.e("伺服器錯誤","${response.code()} ${response.message()}")
                        else ->Log.e("其他錯誤","${response.code()} ${response.message()}")
                    }
                }
                //發送失敗執行此方法
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("查詢失敗","$e")
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //註銷Receiver
        unregisterReceiver(receiver)
    }
}

class Data {
    lateinit var result: Result

    class Result {
        lateinit var results : Array<Results>

        class Results {
            val Station = ""    //站名
            val Destination = ""    //目的地
        }
    }
}