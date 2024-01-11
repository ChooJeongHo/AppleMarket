package com.example.applemarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.databinding.ItemBinding
import java.text.NumberFormat

class GoodsAdapter(val items: MutableList<Item>) : RecyclerView.Adapter<GoodsAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    interface ItemLongClick {
        fun onLongClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null
    var itemLongClick : ItemLongClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsAdapter.Holder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    // 뷰 홀더에 데이터를 설정 (반복 실행됨) - 10번 실행됨
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        holder.itemView.setOnLongClickListener OnLongClickListener@{
            itemLongClick?.onLongClick(it, position)
            return@OnLongClickListener true
        }
        holder.iconIV.setImageResource(items[position].image)
        holder.name.text = items[position].name
        holder.address.text = items[position].address
//        holder.price.text = items[position].price.toString()
        holder.chats.text = items[position].chats.toString()
        holder.likes.text = items[position].likes.toString()

        val priceFormat = NumberFormat.getNumberInstance().format(item.price)
        holder.price.text = "${priceFormat}원"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val iconIV = binding.ivItem
        val name = binding.tvItemName
        val address = binding.tvItemAddress
        val price = binding.tvItemPrice
        val chats = binding.tvItemChats
        val likes = binding.tvItemLikes
        val ivLike = binding.ivItemLikes
    }
}