package com.apitesting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apitesting.ShoeItem
import com.bumptech.glide.Glide

class ShoeItemRecyclerViewAdapter(private var shoeList: List<Shoe>) :
    RecyclerView.Adapter<ShoeItemRecyclerViewAdapter.ViewHolder>() {

    companion object {
        val shoeImages = listOf(
            ShoeImage(1, "https://www.sneakersphere.online/images/1.png"),
            ShoeImage(2, "https://www.sneakersphere.online/images/2.png"),
            ShoeImage(3, "https://www.sneakersphere.online/images/3.png"),
            ShoeImage(4, "https://www.sneakersphere.online/images/4.png"),
            ShoeImage(5, "https://www.sneakersphere.online/images/5.png"),
            ShoeImage(6, "https://www.sneakersphere.online/images/6.png"),
            ShoeImage(7, "https://www.sneakersphere.online/images/7.png"),
            ShoeImage(8, "https://www.sneakersphere.online/images/8.png"),
            ShoeImage(9, "https://www.sneakersphere.online/images/9.png"),
            ShoeImage(10, "https://www.sneakersphere.online/images/10.png")
        )
    }

    fun setData(newShoeList: List<Shoe>) {
        shoeList = ArrayList(newShoeList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImageView: ImageView = itemView.findViewById(R.id.productImageView)
        val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)

        fun bind(shoeItem: Shoe, shoeImages: List<ShoeImage>) {
            productNameTextView.text = "Product Name: ${shoeItem.name}"
            productPriceTextView.text = "Product Price: ${shoeItem.price}"

            val shoeImage = shoeImages.find { it.shoeId == shoeItem.id }

            // Load image using Glide if a matching ShoeImage is found
            if (shoeImage != null) {
                Glide.with(itemView.context)
                    .load(shoeImage.imageUrls)
                    .placeholder(R.drawable.placeholder_image) // Placeholder image while loading
                    .error(R.drawable.baseline_error_outline_24) // Error image if loading fails
                    .into(productImageView)
            } else {
                // Handle case where no matching ShoeImage is found
                // You can set a default image or take another action here
                Glide.with(itemView.context)
                    .load(R.drawable.baseline_error_outline_24)
                    .placeholder(R.drawable.placeholder_image) // Placeholder image while loading
                    .error(R.drawable.baseline_error_outline_24) // Error image if loading fails
                    .into(productImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoeItem = shoeList[position]
        holder.bind(shoeItem, shoeImages)
    }

    override fun getItemCount(): Int {
        return shoeList.size
    }
}