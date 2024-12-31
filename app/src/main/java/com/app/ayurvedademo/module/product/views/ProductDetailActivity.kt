package com.app.ayurvedademo.module.product.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.app.ayurvedademo.R
import com.app.ayurvedademo.base.MyApplication
import com.app.ayurvedademo.databinding.ActivityProductDetailBinding
import com.app.ayurvedademo.module.cart.viewModel.CartViewModel
import com.app.ayurvedademo.module.cart.viewModel.CartViewModelFactory
import com.app.ayurvedademo.module.cart.views.CartListActivity
import com.bumptech.glide.Glide

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var cartViewModel: CartViewModel
    lateinit var binding: ActivityProductDetailBinding
    private var product_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        product_id = intent.getIntExtra("product_id", 0)
        initViewModel()
        cartViewModel.getProductDetail(product_id)
        observer()
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnAddToCart.setOnClickListener {
            startActivity(Intent(this, CartListActivity::class.java))
        }
    }

    private fun initViewModel() {
        val application = application as MyApplication
        val factory = CartViewModelFactory(application)
        cartViewModel = ViewModelProvider(application, factory).get(CartViewModel::class.java)
    }

    private fun observer() {
        cartViewModel.productDetail.observe(this) {
            binding.tvProductName.text = it.productName
            binding.tvProductDescription.text = it.productDescription
            binding.tvPrice.text = "$" + it.productPrice
            Glide.with(this).load(it.productImage).into(binding.ivProductImage)
        }
    }
}