package com.app.ayurvedademo.module.cart.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.app.ayurvedademo.R
import com.app.ayurvedademo.base.MyApplication
import com.app.ayurvedademo.databinding.ActivityCartListBinding
import com.app.ayurvedademo.module.cart.adapter.CartListAdapter
import com.app.ayurvedademo.module.cart.viewModel.CartViewModel
import com.app.ayurvedademo.module.cart.viewModel.CartViewModelFactory
import com.app.ayurvedademo.module.product.model.ProductListModel
import com.app.ayurvedademo.module.product.views.ProductDetailActivity

class CartListActivity : AppCompatActivity() {

    private lateinit var bindind: ActivityCartListBinding
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindind=ActivityCartListBinding.inflate(layoutInflater)
        setContentView(bindind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val application = application as MyApplication
        val factory = CartViewModelFactory(application)
        cartViewModel = ViewModelProvider(application, factory).get(CartViewModel::class.java)
        cartViewModel.getCartList()
        observer()
        bindind.btnBack.setOnClickListener {
            onBackPressed()
        }
    }


    private fun observer(){
        cartViewModel.getCartList.observe(this){
            setCartListAdapter(it)
        }
    }

    private fun setCartListAdapter(data:ArrayList<ProductListModel>) {
        val adapter=CartListAdapter(this, data,cartListCallback)
        bindind.rvCartList.adapter=adapter
    }


    private var cartListCallback = object :CartListAdapter.CartListCallback{
        override fun onAddProductQuantity(id: Int) {
         cartViewModel.addProductQuantity(id)
        }

        override fun onRemoveProductQuantity(id: Int) {
            cartViewModel.removeProductQuantity(id)
        }

        override fun getProductDetail(id: Int) {
            var intent = Intent(this@CartListActivity, ProductDetailActivity::class.java)
            intent.putExtra("product_id", id)
            startActivity(intent)
        }

    }
}