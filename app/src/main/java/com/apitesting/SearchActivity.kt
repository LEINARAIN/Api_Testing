package com.apitesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var buttonSearch: ImageButton
    private lateinit var searchBackButton: ImageButton
    private lateinit var recyclerViewResults: RecyclerView
    private lateinit var searchAdapter: SearchAdapter

    private var shoeList: MutableList<Shoe> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchEditText = findViewById(R.id.searchEditTextSearch)
        buttonSearch = findViewById(R.id.buttonSearch)
        recyclerViewResults = findViewById(R.id.recyclerViewResults)
        searchBackButton = findViewById(R.id.searchBackButton)

        // Initialize adapter
        searchAdapter = SearchAdapter(emptyList()) { shoe, imageUrl ->
            val intent = Intent(this, ShoeItemDetails::class.java)
            intent.putExtra("SHOE_NAME", shoe.name)
            intent.putExtra("SHOE_PRICE", shoe.price)
            intent.putExtra("SHOE_DESCRIPTION", shoe.description)
            intent.putExtra("SHOE_IMAGE_URL", imageUrl)
            startActivity(intent)
        }

        // Set up RecyclerView
        recyclerViewResults.layoutManager = LinearLayoutManager(this)
        recyclerViewResults.adapter = searchAdapter

        //Calling Retrofit
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ShoeService::class.java)

        val responseLiveData: LiveData<Response<ShoeItem>> =
            liveData {
                val response = retrofitService.getShoeItem()
                emit(response)
            }

        responseLiveData.observe(this, Observer { response ->
            val shoeItem = response.body()
            shoeItem?.let {
                val shoeList = it.shoes
                searchAdapter.setData(shoeList) // Update adapter data with the fetched list
            }
        })

        //buttonSearch
        buttonSearch.setOnClickListener {
            val query = searchEditText.text.toString().trim()
            searchAdapter.performSearch(query)
        }

        // Set up searchEditText key listener to trigger search on Enter key press
        searchEditText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val query = searchEditText.text.toString().trim()
                searchAdapter.performSearch(query) // Call performSearch function of adapter to filter the list based on query
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        //back button
        searchBackButton.setOnClickListener {
            onBackPressed()
        }
    }
}
