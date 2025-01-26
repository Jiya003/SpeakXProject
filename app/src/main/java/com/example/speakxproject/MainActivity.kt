package com.example.speakxproject

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.speakxproject.services.MockApiService

class MainActivity : AppCompatActivity() {

    private lateinit var mockApiService: MockApiService
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var loadingText: TextView
    private var currentPage = 0
    private val itemList: MutableList<String> = mutableListOf()
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize MockApiService
        mockApiService = MockApiService()

        // Set up ListView and Adapter
        val listView: ListView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        listView.adapter = adapter

        // Set up loading text
        loadingText = findViewById(R.id.textLoading)

        // Load the first page of data
        loadData()

        // Set a scroll listener for pagination
        listView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}

            override fun onScroll(
                view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int
            ) {
                // Check if we've reached the bottom of the list and aren't already loading data
                if (!isLoading && !isLastPage && firstVisibleItem + visibleItemCount >= totalItemCount) {
                    loadData()
                }
            }
        })
    }

    private fun loadData() {
        if (isLoading) return

        isLoading = true

        // Show loading text
        loadingText.visibility = View.VISIBLE

        // Simulate data fetching
        mockApiService.fetchItems(currentPage) { newItems ->
            if (newItems.isEmpty()) {
                // If no more data, set the last page flag
                isLastPage = true
            } else {
                // Add new items to the list and update the adapter
                itemList.addAll(newItems)
                adapter.notifyDataSetChanged()
                currentPage++
            }

            isLoading = false

            // Hide loading text
            loadingText.visibility = View.GONE
        }
    }
}
