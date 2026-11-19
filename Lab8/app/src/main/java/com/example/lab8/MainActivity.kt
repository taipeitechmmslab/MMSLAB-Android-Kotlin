package com.example.lab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var myAdapter: MyAdapter
    private val contacts = ArrayList<Contact>()

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 優化：使用 let 安全處理回傳的 Intent
            result.data?.let { intent ->
                val name = intent.getStringExtra("name").orEmpty()
                val phone = intent.getStringExtra("phone").orEmpty()
                contacts.add(Contact(name, phone))
                // 優化：使用 notifyItemInserted 局部更新，取代效能較差的 notifyDataSetChanged()
                myAdapter.notifyItemInserted(contacts.size - 1)
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

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        // 優化：使用 apply 簡化 LayoutManager 與 RecyclerView 初始化
        myAdapter = MyAdapter(contacts)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            adapter = myAdapter
        }

        btnAdd.setOnClickListener {
            startForResult.launch(Intent(this, SecActivity::class.java))
        }
    }
}
