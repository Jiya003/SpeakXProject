package com.example.speakxproject.services

import android.os.Handler
import android.os.Looper

class MockApiService {

    // Simulate fetching data from an API with a delay
    fun fetchItems(page: Int, callback: (List<String>) -> Unit) {
        // Simulate network delay
        Handler(Looper.getMainLooper()).postDelayed({
            // Return a mock list of data
            val startIndex = page * 10
            val endIndex = startIndex + 9
            val mockData = (startIndex..endIndex).map { "Item $it" }
            callback(mockData)
        }, 3000) // Simulate a 2-second network delay
    }
}