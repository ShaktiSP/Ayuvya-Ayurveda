package com.app.ayurvedademo.module.cart.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.ayurvedademo.R
import com.app.ayurvedademo.module.product.model.ProductListModel

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private var cartList: ArrayList<ProductListModel> = ArrayList()
    var productlist: ArrayList<ProductListModel> = ArrayList()
    var getCartList: MutableLiveData<ArrayList<ProductListModel>> = MutableLiveData()
    var getCartCount: MutableLiveData<Int> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()
    var productDetail: MutableLiveData<ProductListModel> = MutableLiveData()

    init {
        getProductList()
    }

    fun addToCart(productListModel: ProductListModel) {
        if (cartList.contains(productListModel)) {
            error.value = "Already Added"
        } else {
            cartList.add(productListModel)
            getCartCount.value = cartList.size
        }
    }

    fun getCartList() {
        getCartList.value = cartList
    }

    fun getProductDetail(id: Int) {
        for (i in productlist) {
            if (i.id == id) {
                productDetail.value = i
            }
        }
    }

    fun addProductQuantity(id: Int) {
        for (i in cartList) {
            if (i.id == id) {
                i.productQuantity += 1
            }
        }
        getCartList.value = cartList
    }

    fun removeProductQuantity(id: Int) {
        for (i in cartList) {
            if (i.id == id) {
                if (i.productQuantity != 1) {
                    i.productQuantity -= 1
                }
            }
        }
        getCartList.value = cartList
    }

    fun getProductList() {
        productlist.add(
            ProductListModel(
                R.drawable.product_image_one,
                "Black Zipper Faux Leather Jacket",
                1,
                "Make your holiday gathering a little less formal in this casual jacket, made from 100% polyester. Keeping you center stage, this black relaxed fit piece elevates your outerwear collection to a new level. With its full sleeve it is the perfect layering piece for transeasonal weather.",
                "3999",
                1
            )
        )
        productlist.add(
            ProductListModel(
                R.drawable.product_image_two,
                "Black Multi-Zip Relaxed Fit Jacket",
                2,
                "This Black Jacket offers premium winter protection with its warm fabric composition. Street-style inspired details such as full sleeve and baseball collar complete the timeless look. The perfect blend of warmth and style.",
                "3499",
                1
            )
        )
        productlist.add(
            ProductListModel(
                R.drawable.product_image_three,
                "Legends Anarchy Light Brown Varsity Jacket",
                3,
                "Step into timeless style with our Legends Collection—crafted for those who make a statement wherever they go. This collection celebrates blending the elegance of the past with a bold, rebellious edge.",
                "3899",
                1
            )
        )
        productlist.add(
            ProductListModel(
                R.drawable.product_image_four,
                "Legends Vandal Burgundy Varsity Jacket",
                4,
                "Step into timeless style with our Legends Collection—crafted for those who make a statement wherever they go. This collection celebrates blending the elegance of the past with a bold, rebellious edge.",
                "3999",
                1
            )
        )
        productlist.add(
            ProductListModel(
                R.drawable.product_image_five,
                "Brown Sleeveless Puffer Vest",
                5,
                "This orange Jacket offers premium winter protection with its warm fabric composition. Street-style inspired details such as sleeveless style and high neck collar complete the timeless look. The perfect blend of warmth and style.",
                "2399",
                1
            )
        )
    }

}