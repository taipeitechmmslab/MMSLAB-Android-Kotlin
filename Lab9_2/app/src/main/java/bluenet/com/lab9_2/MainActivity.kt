package bluenet.com.lab9_2

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_calculate.setOnClickListener {
            when{
                //判斷使用者是否輸入身高與體重
                ed_height.length()<1 ->Toast.makeText(this,"請輸入身高",
                    Toast.LENGTH_SHORT).show()
                ed_weight.length()<1 ->Toast.makeText(this,"請輸入體重",
                    Toast.LENGTH_SHORT).show()
                else-> {
                    runAsyncTask()
                    //隱藏鍵盤
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(it.windowToken,0)
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun runAsyncTask(){
        object : AsyncTask<Void, Int, Boolean>() {
            override fun onPreExecute() {
                super.onPreExecute()
                //初始化『標準體重』與『體脂肪』
                tv_weight.text = "標準體重\n無"
                tv_bmi.text = "體脂肪\n無"
                //初始化進度條
                progressBar2.progress = 0
                tv_progress.text = "0%"
                //顯示進度條
                ll_progress.visibility = View.VISIBLE
            }

            override fun doInBackground(vararg voids: Void): Boolean? {
                var progress = 0
                while (progress < 100)
                    try {
                        //延遲50ms
                        Thread.sleep(50)
                        //更新進度
                        publishProgress(progress)
                        //計數器加一
                        progress++
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                return true
            }

            override fun onProgressUpdate(vararg values: Int?) {
                super.onProgressUpdate(*values)
                values[0]?.let {
                    //顯示計數器數值
                    progressBar2.progress = it
                    tv_progress.text = String.format("%d%%", it)
                }
            }

            override fun onPostExecute(status: Boolean?) {
                //隱藏進度條
                ll_progress.visibility = View.GONE
                //讀取身高與體重
                val cal_height = ed_height.text.toString().toDouble()
                val cal_weight = ed_weight.text.toString().toDouble()
                val cal_standweight: Double
                val cal_bodyfat: Double
                //判斷性別，跳用各自的計算公式
                if (btn_boy.isChecked) {
                    cal_standweight = (cal_height - 80) * 0.7
                    cal_bodyfat = (cal_weight - 0.88 * cal_standweight) / cal_weight * 100
                } else {
                    cal_standweight = (cal_height - 70) * 0.6
                    cal_bodyfat = (cal_weight - 0.82 * cal_standweight) / cal_weight * 100
                }
                //顯示計算結果
                tv_weight.text = String.format("標準體重 \n%.2f", cal_standweight)
                tv_bmi.text = String.format("體脂肪 \n%.2f", cal_bodyfat)
            }
        }.execute() //執行AsyncTask
    }
}
