package com.shrikissan.user.network

class Shop {
    companion object{
       // const val baseUrl = "http://ec2-23-23-58-84.compute-1.amazonaws.com"
       const val baseUrl = "http://192.168.177.62:9999"
    }
    class Auth{
        companion object{
            const val login = "/auth/login"
            const val verify = "/auth/verify"
        }
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