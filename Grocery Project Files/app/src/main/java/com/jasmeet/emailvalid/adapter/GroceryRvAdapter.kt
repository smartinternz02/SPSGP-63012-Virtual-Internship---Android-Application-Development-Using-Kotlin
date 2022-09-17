package com.jasmeet.emailvalid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jasmeet.emailvalid.databinding.GroceryRvItemsBinding
import com.jasmeet.emailvalid.roomDB.GroceryItems

class GroceryRvAdapter(var list: List<GroceryItems>,val groceryItemClickInterface:GroceryItemClickInterface):RecyclerView.Adapter<GroceryRvAdapter.GroceryViewHolder>() {


    inner class GroceryViewHolder(var binding:GroceryRvItemsBinding):RecyclerView.ViewHolder(binding.root){


    }

    interface GroceryItemClickInterface {
        fun onItemClick(groceryItems: GroceryItems) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {

        return GroceryViewHolder(
            GroceryRvItemsBinding.inflate(
                LayoutInflater.from
                    (parent.context),
                parent,
                false
            )
        )


    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {

        holder.binding.itemName.text = list[position].itemName
        holder.binding.itemQuantity.text = list[position].itemQuantity.toString()
        holder.binding.itemRate.text = "₹ "+ list[position].itemPrice.toString()

        val itemTotal :Float = list[position].itemPrice * list[position].itemQuantity

        holder.binding.totalAmount.text = "₹ $itemTotal"

        holder.binding.delete.setOnClickListener{
            groceryItemClickInterface.onItemClick(list[position])

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}