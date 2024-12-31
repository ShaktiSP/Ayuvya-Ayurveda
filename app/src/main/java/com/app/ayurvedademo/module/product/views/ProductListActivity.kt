package com.app.ayurvedademo.module.product.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.app.ayurvedademo.R
import com.app.ayurvedademo.base.MyApplication
import com.app.ayurvedademo.databinding.ActivityProductListBinding
import com.app.ayurvedademo.module.cart.viewModel.CartViewModel
import com.app.ayurvedademo.module.cart.viewModel.CartViewModelFactory
import com.app.ayurvedademo.module.cart.views.CartListActivity
import com.app.ayurvedademo.module.product.adapter.ProductListAdapter
import com.app.ayurvedademo.module.product.model.ProductListModel

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val application = application as MyApplication
        val factory = CartViewModelFactory(application)

        cartViewModel = ViewModelProvider(application, factory).get(CartViewModel::class.java)
        binding.btnAddToCart.setOnClickListener {
            startActivity(Intent(this, CartListActivity::class.java))
        }
        setProductListAdapter()
        observer()
    }

    private fun setProductListAdapter() {
        productListAdapter =
            ProductListAdapter(this, cartViewModel.productlist, productListCallback)
        binding.rvProductList.adapter = productListAdapter
    }

    private var productListCallback = object : ProductListAdapter.ProductListCallback {
        override fun onClickAddToCart(product: ProductListModel) {
            cartViewModel.addToCart(product)
        }

        override fun getProductDetail(id: Int) {
            var intent = Intent(this@ProductListActivity, ProductDetailActivity::class.java)
            intent.putExtra("product_id", id)
            startActivity(intent)
        }
    }

    private fun observer() {
        cartViewModel.getCartCount.observe(this) {
            if (it != 0) {
                binding.tvCartCount.text = it.toString()
                binding.rlCartCount.visibility = View.VISIBLE
            } else {
                binding.tvCartCount.text = it.toString()
                binding.rlCartCount.visibility = View.GONE
            }
        }

        cartViewModel.error.observe(this) {
            Toast.makeText(this, "Already Added", Toast.LENGTH_SHORT).show()
        }
    }

}