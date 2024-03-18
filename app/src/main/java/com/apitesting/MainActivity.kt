package com.apitesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.apitesting.databinding.ActivityMainBinding
import retrofit2.Response
import androidx.lifecycle.liveData
import com.bumptech.glide.Glide

//import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val retrofitService = RetrofitInstance.getRetrofitInstance().create(ShoeService::class.java)

        //val responseLiveData: LiveData<Response<ShoeItem>> =
          //     liveData {
            //       val response = retrofitService.getShoeItem()
              //     emit(response)

               }


        //responseLiveData.observe(this, Observer { response ->
         //val shoeItem = response.body()
           // shoeItem?.let {
             //   val shoeList = it.shoes
               // for (shoeItem in shoeList) {
                 //   val shoeId = "Shoe Id: ${shoeItem.id} \n"
                   // binding.titleTextView.append(shoeId)
                    //val shoeName = "Shoe Name: ${shoeItem.name} \n"
                    //binding.titleTextView.append(shoeName)
                    //val shoeDescription = "Shoe Description: ${shoeItem.description} \n"
                    //binding.titleTextView.append(shoeDescription)
                    //val shoePrice = "Shoe Price: ${shoeItem.price} \n"
                    //binding.titleTextView.append(shoePrice)
                //}
            //}
         //})

        //val url: String = "https://sneakersphere.online/frontendimg/img/jordan1.png"
        //val imageView : ImageView = findViewById (R.id.imageView)
        //Glide.with(this).load(url).into(imageView)

    //}
}


