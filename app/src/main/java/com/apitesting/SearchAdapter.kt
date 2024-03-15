// SearchAdapter.kt

package com.apitesting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SearchAdapter(
    private var shoeList: List<Shoe>,
    private val onItemClick: (shoe: Shoe, imageUrl: String) -> Unit
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

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

    //Access shoeImages using the companion object
    fun getShoeImageUrl(shoeId: Int): String {
        val shoeImage = shoeImages.find { it.shoeId == shoeId }
        return shoeImage?.imageUrls ?: ""
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImageView: ImageView = itemView.findViewById(R.id.searchProductImageView)
        val productNameTextView: TextView = itemView.findViewById(R.id.searchProductNameTextView)
        val productPriceTextView: TextView = itemView.findViewById(R.id.searchProductPriceTextView)

        fun bind(shoeItem: Shoe) {
            productNameTextView.text = "${shoeItem.name}"
            productPriceTextView.text = "PHP ${shoeItem.price}"

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shoe, parent, false)
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

    fun setData(newShoeList: List<Shoe>) {
        shoeList = ArrayList(newShoeList)
        notifyDataSetChanged()
    }

    fun performSearch(query: String) {
        val filteredList = shoeList.filter { shoe ->
            // Filter condition: Check if the shoe name contains any of the query keywords (case-insensitive)
            val keywords = query.trim().split("\\s+".toRegex()) // Split query into keywords
            keywords.all { keyword ->
                shoe.name.split("\\s+".toRegex()).any { shoeKeyword ->
                    shoeKeyword.contains(keyword, ignoreCase = true)
                }
            }
        }
        setData(filteredList)
    }

}