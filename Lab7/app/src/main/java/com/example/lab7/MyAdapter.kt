package com.example.lab7

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinner = findViewById<Spinner>(R.id.spinner)
        val listView = findViewById<ListView>(R.id.listView)
        val gridView = findViewById<GridView>(R.id.gridView)

        val count = mutableListOf<String>()
        val items = mutableListOf<Item>()
        val priceRange = 10..100

        // 優化：使用 try/finally 確保 TypedArray 資源必定釋放
        val array = resources.obtainTypedArray(R.array.image_list)
        try {
            for (index in 0 until array.length()) {
                val photo = array.getResourceId(index, 0)
                val name = "水果${index + 1}"
                val price = priceRange.random()
                count.add("${index + 1}個")
                items.add(Item(photo, name, price))
            }
        } finally {
            array.recycle()
        }

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, count)
        gridView.numColumns = 3
        gridView.adapter = MyAdapter(this, items, R.layout.adapter_vertical)
        listView.adapter = MyAdapter(this, items, R.layout.adapter_horizontal)

        // 優化：新增點擊事件，連動 Spinner 顯示所選水果與數量
        val onItemClick = { position: Int ->
            val selectedItem = items[position]
            val selectedCount = spinner.selectedItem.toString()
            Toast.makeText(
                this,
                "已選擇 ${selectedItem.name}（${selectedItem.price}元）x $selectedCount",
                Toast.LENGTH_SHORT
            ).show()
        }

        gridView.setOnItemClickListener { _, _, position, _ -> onItemClick(position) }
        listView.setOnItemClickListener { _, _, position, _ -> onItemClick(position) }
    }
}
