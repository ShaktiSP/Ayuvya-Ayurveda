package com.app.ayurvedademo.module.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ayurvedademo.databinding.ItemCartListBinding
import com.app.ayurvedademo.module.product.model.ProductListModel
import com.bumptech.glide.Glide

class CartListAdapter(var context: Context, var data: ArrayList<ProductListModel>,var callback:CartListCallback) :
    RecyclerView.Adapter<CartListAdapter.ViewHolder>() {


    inner class ViewHolder(var binding: ItemCartListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
            binding.tvProductDescription.text = data.get(position).productDescription
            binding.tvPrice.text = "$" + data.get(position).productPrice
            binding.tvProductName.text = "$" + data.get(position).productName
            binding.tvQuantity.text = data.get(position).productQuantity.toString()
            Glide.with(context).load(data.get(position).productImage).into(binding.ivProduct)
            binding.btnAddQuantity.setOnClickListener {
                callback.onAddProductQuantity(data.get(position).id)
            }
            binding.btnRemoveQuantity.setOnClickListener {
                callback.onRemoveProductQuantity(data.get(position).id)
            }
            binding.root.setOnClickListener {
                callback.getProductDetail(data.get(position).id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCartListBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }


    interface  CartListCallback{
        fun onAddProductQuantity(id:Int)
        fun onRemoveProductQuantity(id:Int)
        fun getProductDetail(id: Int)
    }
}