package com.example.speakxproject.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class HighlightedAdapter(private val dataList: List<String>, var query: String) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(parent?.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        val itemText = dataList[position]

        // Highlight search text in the item
        textView.text = highlightText(itemText, query)

        return view
    }

    private fun highlightText(text: String, query: String): CharSequence {
        if (query.isEmpty()) {
            return text // No highlighting if query is empty
        }

        val spannable = SpannableString(text)
        val startIndex = text.indexOf(query, ignoreCase = true)

        // If query found, highlight all occurrences
        var currentIndex = startIndex
        while (currentIndex != -1) {
            val endIndex = currentIndex + query.length
            spannable.setSpan(
                ForegroundColorSpan(Color.RED), // Set the color for highlighting
                currentIndex, endIndex,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            currentIndex = text.indexOf(query, currentIndex + query.length, ignoreCase = true)
        }

        return spannable
    }
}
