package com.shrikissan.user.network

class Shop {
    companion object{
        const val baseUrl = "http://192.168.146.62:9999"
    }
    class Category{
        companion object{
            const val list = "/shop/category/list"
            const val add = "/shop/category/add"
        }
    }
    class Product{
        companion object {
            const val list = "/shop/product/list"
            const val add = "/shop/product/add"
        }
    }
    class User{
        companion object{
            const val get = ""
            const val add = ""
        }
    }
}