package com.milk.happymessage.common.pay

import com.android.billingclient.api.ProductDetails

data class ProductsModel(var productDetails: ProductDetails? = null, var productsNames: String = "")