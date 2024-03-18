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
    private val onItemClick: (shoe: Shoe, imageUrl: String?) -> Unit
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    companion object {
        private val shoeImages = mapOf(
            1 to "https://www.sneakersphere.online/images/1.png",
            2 to "https://www.sneakersphere.online/images/2.png",
            3 to "https://www.sneakersphere.online/images/3.png",
            4 to "https://www.sneakersphere.online/images/4.png",
            5 to "https://www.sneakersphere.online/images/5.png",
            6 to "https://www.sneakersphere.online/images/6.png",
            7 to "https://www.sneakersphere.online/images/7.png",
            8 to "https://www.sneakersphere.online/images/8.png",
            9 to "https://www.sneakersphere.online/images/9.png",
            10 to "https://www.sneakersphere.online/images/10.png",
            11 to "https://www.sneakersphere.online/images/11.png",
            12 to "https://www.sneakersphere.online/images/12.png"

            // Add more shoe IDs and corresponding image URLs as needed
        )
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTextView: TextView = itemView.findViewById(R.id.searchProductNameTextView)

        fun bind(shoeItem: Shoe) {
            productNameTextView.text = "${shoeItem.name}"

            // Set click listener
            itemView.setOnClickListener {
                val imageUrl = getShoeImageUrl(shoeItem.id)
                onItemClick(shoeItem, imageUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shoe, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoeItem = shoeList[position]
        holder.bind(shoeItem)
    }

    override fun getItemCount(): Int {
        return shoeList.size
    }

    fun setData(newShoeList: List<Shoe>) {
        shoeList = newShoeList
        notifyDataSetChanged()
    }

    private fun getShoeImageUrl(shoeId: Int): String? {
        return shoeImages[shoeId]
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
