package com.apitesting

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ShoeItemRecyclerViewAdapter(
    private var shoeList: List<Shoe>,
    private val onItemClick: (shoe: Shoe, imageUrl: String) -> Unit
) :
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
            ShoeImage(10, "https://www.sneakersphere.online/images/10.png"),
            ShoeImage(11, "https://www.sneakersphere.online/images/11.png"),
            ShoeImage(12, "https://www.sneakersphere.online/images/12.png")
        )
    }

    fun getShoeImageUrl(shoeId: Int): String {
        // Access shoeImages using the companion object
        val shoeImage = shoeImages.find { it.shoeId == shoeId }
        return shoeImage?.imageUrls ?: ""
    }

    fun setData(newShoeList: List<Shoe>) {
        shoeList = ArrayList(newShoeList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImageView: ImageView = itemView.findViewById(R.id.productImageView)
        val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)

        fun bind(shoeItem: Shoe) {
            productNameTextView.text = "Product Name: ${shoeItem.name}"
            productPriceTextView.text = "Product Price: ${shoeItem.price}"

            val shoeImageUrl = getShoeImageUrl(shoeItem.id)

            // Load image using Glide
            Glide.with(itemView.context)
                .load(shoeImageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.baseline_error_outline_24)
                .into(productImageView)

            // Set click listener
            itemView.setOnClickListener { onItemClick(shoeItem, shoeImageUrl) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoeItem = shoeList[position]
        holder.bind(shoeItem)
        holder.itemView.setOnClickListener { onItemClick(shoeItem, getShoeImageUrl(shoeItem.id)) }
    }

    override fun getItemCount(): Int {
        return shoeList.size
    }
}
