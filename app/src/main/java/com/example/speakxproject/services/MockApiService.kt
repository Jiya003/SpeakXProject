package com.example.speakxproject.services

import android.os.Handler
import android.os.Looper

class MockApiService {

    // Simulate fetching data from an API with a delay
    fun fetchItems(page: Int, callback: (List<String>) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            val startIndex = page * 10
            val endIndex = startIndex + 9

            // Simulate last page by returning empty list if no more data
            val mockData = if (page < 5) { // Assume 5 pages of data
                (startIndex..endIndex).map { "Item $it" }
            } else {
                emptyList()
            }

            callback(mockData)
        }, 1500) // Simulated 1.5-second delay
    }
}
