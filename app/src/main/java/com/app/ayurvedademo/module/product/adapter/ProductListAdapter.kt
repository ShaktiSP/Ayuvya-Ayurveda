package com.app.ayurvedademo.module.product.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ayurvedademo.databinding.ItemProductListBinding
import com.app.ayurvedademo.module.product.model.ProductListModel
import com.bumptech.glide.Glide

class ProductListAdapter(
    var context: Context,
    var data: ArrayList<ProductListModel>,
    var callback: ProductListCallback
) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
            binding.tvProductDescription.text = data.get(position).productDescription
            binding.tvPrice.text = "$" + data.get(position).productPrice
            binding.tvProductName.text = "$" + data.get(position).productName
            binding.btnAddToCart.setOnClickListener {
                callback.onClickAddToCart(data[position])
            }
            Glide.with(context).load(data.get(position).productImage).into(binding.ivProduct)

            binding.root.setOnClickListener {
                callback.getProductDetail(data[position].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductListBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    interface ProductListCallback {
        fun onClickAddToCart(product: ProductListModel)
        fun getProductDetail(id: Int)
    }
}