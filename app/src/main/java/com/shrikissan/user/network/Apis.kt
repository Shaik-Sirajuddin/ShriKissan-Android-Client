package com.shrikissan.user.network

class Shop {
    companion object {
         const val baseUrl = "http://ec2-23-23-58-84.compute-1.amazonaws.com"
        //const val baseUrl = "http://192.168.69.62:9999"
    }

    class Auth {
        companion object {
            const val login = "/auth/login"
            const val verify = "/auth/verify"
        }
    }

    class Category {
        companion object {
            const val list = "/shop/category/list"
            const val add = "/shop/category/add"
        }
    }
    class Product {
        companion object {
            const val listByCategory= "/shop/product/list"
            const val getAll = "/shop/product/all"
            const val get = "/shop/product/get"
        }
    }

    class User {
        companion object {
            const val get = "/user/profile/"
            const val addAddress = "/user/profile/address/add"
            const val editAddress = "/user/profile/address/edit"
            const val updateProfile = "/user/profile/update"
            const val putAndGet = "/user/upload-image"
        }

        class Cart {
            companion object {
                const val get = "/user/cart"
                const val add = "/user/cart/add"
                const val increase = "/user/cart/increase"
                const val decrease = "/user/cart/decrease"
                const val delete = "/user/cart/delete"
            }
        }
    }

    class Order {
        companion object {
            const val listOrders = "/order/list"
            const val listAddress = "/order/address/list"
            const val addAddress = "/order/address/add"
            const val addReview = "/order/reviews/add"
            const val getReview = "/order/reviews/get"
            const val removeAddress = "/order/address/delete"
            const val editAddress = "/order/address/edit"
        }
    }
}