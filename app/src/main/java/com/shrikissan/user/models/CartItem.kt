package com.shrikissan.user.models

data class CartItem(
    val product: Product,
    val subProduct: SubProduct,
    val quantity:Int,
)
