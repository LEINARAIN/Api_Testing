package com.apitesting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apitesting.databinding.ActivityDashboardBinding
import retrofit2.Response

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.productRecyclerView)

        val adapter = ShoeItemRecyclerViewAdapter(emptyList()) // Initialize adapter with empty list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

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
                adapter.setData(shoeList)
            }
        })
    }
}