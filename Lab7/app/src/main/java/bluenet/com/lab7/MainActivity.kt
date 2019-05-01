package bluenet.com.lab7

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val items = ArrayList<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //從R讀取圖片資源
        val imgArray = resources.obtainTypedArray(R.array.imgArray)
        val nameArray = resources.getStringArray(R.array.nameArray)
        //建立項目物件，放入圖片資源與名稱
        for(i in 0 until imgArray.length())
            items.add(Item(imgArray.getResourceId(i,0), nameArray[i]))
        //回收TypedArray
        imgArray.recycle()
        //連結Adapter，設定layout為adapter_horizontal
        spinner.adapter = MyAdapter(R.layout.adapter_horizontal, items)
        //設定橫向顯示的項目筆數
        gridview.numColumns = 3
        //連結Adapter，設定layout為adapter_vertical
        gridview.adapter = MyAdapter(R.layout.adapter_vertical, items)
        //gridview.adapter = MyAdapter2(R.layout.adapter_vertical, items)
        //連結Adapter，設定layout為simple_list_item_1與字串陣列
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
            arrayListOf("項目1","項目2","項目3","項目4","項目5","項目6","項目7","項目8","項目9"))
    }
}
//自定義類別
data class Item(
    val photo: Int,     //圖片Resource
    val name: String    //名稱
)