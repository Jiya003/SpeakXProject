package com.example.speakxproject

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.speakxproject.services.MockApiService
import android.widget.AbsListView
import com.facebook.shimmer.ShimmerFrameLayout


class MainActivity : AppCompatActivity() {

    private lateinit var mockApiService: MockApiService
    private lateinit var adapter: ArrayAdapter<String>
    private var currentPage = 0
    private val itemList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize MockApiService
        mockApiService = MockApiService()

        // Set up ListView and Adapter
        val listView: ListView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        listView.adapter = adapter

        // Load first page of data
        loadData()

        // Set a scroll listener for pagination
        listView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}

            override fun onScroll(
                view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int
            ) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount && !isLoading) {
                    loadData()
                }
            }
        })
    }

    private var isLoading = false

    private fun loadData() {
        val ShimmerFrameLayout: ShimmerFrameLayout = findViewById(R.id.shimmer_view_container)


        if (isLoading) return

        isLoading = true
        mockApiService.fetchItems(currentPage) { newItems ->
            itemList.addAll(newItems)
            adapter.notifyDataSetChanged()
            currentPage++
            isLoading = false
        }

    }
}
