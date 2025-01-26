package com.example.speakxproject

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.speakxproject.services.MockApiService

class MainActivity : AppCompatActivity() {

    private lateinit var mockApiService: MockApiService //For Api service
    private lateinit var adapter: ArrayAdapter<SpannableString>
    private lateinit var searchBar: EditText
    private lateinit var listView: ListView
    private val itemList: MutableList<String> = mutableListOf()
    private val displayedItems: MutableList<SpannableString> = mutableListOf()
    private var currentPage = 0
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initializing MockApiService
        mockApiService = MockApiService()

        //Initializing views
        searchBar = findViewById(R.id.searchBar)
        listView = findViewById(R.id.listView)
        val loadingText: TextView = findViewById(R.id.textLoading)

        //Setting up the ListView and Adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayedItems)
        listView.adapter = adapter

        //Loading the first page of the data
        loadData(loadingText)

        //Implementing the search functionality
        searchBar.addTextChangedListener(object:android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterItems(s.toString())
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        //Pagination
        listView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}

            override fun onScroll(
                view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int
            ) {
                if (!isLoading && !isLastPage && firstVisibleItem + visibleItemCount >= totalItemCount) {
                    loadData(loadingText)
                }
            }
        })
    }

    private fun loadData(loadingText: TextView) {
        if (isLoading) return

        isLoading = true
        loadingText.visibility = View.VISIBLE

        mockApiService.fetchItems(currentPage) { newItems ->
            if (newItems.isEmpty()) {
                isLastPage = true
            } else {
                itemList.addAll(newItems)
                filterItems(searchBar.text.toString()) // Updated the displayed items
                currentPage++
            }

            isLoading = false
            loadingText.visibility = View.GONE
        }
    }

    private fun filterItems(query: String) {
        displayedItems.clear()
        if (query.isEmpty()) {
            displayedItems.addAll(itemList.map { SpannableString(it) })
        } else {
            val lowerCaseQuery = query.lowercase()
            for (item in itemList) {
                val lowerCaseItem = item.lowercase()
                val spannable = SpannableString(item)
                val startIndex = lowerCaseItem.indexOf(lowerCaseQuery)

                if (startIndex >= 0) {
                    spannable.setSpan(
                        ForegroundColorSpan(Color.RED),//Highlight color
                        startIndex,
                        startIndex + query.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannable.setSpan(
                        StyleSpan(android.graphics.Typeface.BOLD),//Bold text
                        startIndex,
                        startIndex + query.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                displayedItems.add(spannable)
            }
        }
        adapter.notifyDataSetChanged()
    }
}
