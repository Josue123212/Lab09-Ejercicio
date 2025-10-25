package com.example.lab09products

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductModel>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductModel
}
