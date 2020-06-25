package com.example.popeyes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popeyes.R
import com.example.popeyes.utils.FoodItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_menu.view.*

class MenuAdapter(private val foodItems: ArrayList<FoodItem>,
                  private val listener: OnClickListener) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = foodItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bindView(foodItems[position], listener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(foodItem: FoodItem?, listener: OnClickListener) {

            itemView.itemName.text = foodItem?.title ?: ""
            itemView.priceTextView.text = foodItem?.price ?: ""
            itemView.descTextView.text = foodItem?.description ?: ""
            Picasso.get()
                    .load(foodItem?.image)
                    .into(itemView.mImageView)
            itemView.orderButton.setOnClickListener {
                listener.onOrderClick(foodItem?.id)
            }
        }
    }

    interface OnClickListener {
        fun onOrderClick(id: Int?)
    }
}