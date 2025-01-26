package com.example.speakxproject.adapter

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class ItemAdapter(
    private var items: List<String>,
    private val searchQuery: String
) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val textView: TextView = view.findViewById(android.R.id.text1)

        val text = items[position]
        textView.text = highlightText(text)
        return view
    }

    // Function to highlight the search query
    private fun highlightText(text: String): Spanned {
        val startIndex = text.indexOf(searchQuery, ignoreCase = true)
        return if (startIndex >= 0) {
            val spannable = SpannableString(text)
            val endIndex = startIndex + searchQuery.length
            spannable.setSpan(ForegroundColorSpan(Color.parseColor("#FF0000FF")), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return spannable

        } else {
            SpannableString(text)
        }
    }

    // Method to update the list of items
    fun updateData(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }
}