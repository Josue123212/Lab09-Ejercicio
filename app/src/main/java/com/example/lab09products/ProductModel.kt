package com.example.lab09products

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("thumbnail") val thumbnail: String
)

// Añadimos el wrapper de respuesta para el endpoint /products de DummyJSON
// La API devuelve un objeto con la clave "products" que contiene la lista
// de ProductModel, además de otras propiedades (total, skip, limit).
// Esto evita errores de parseo y crashes al iniciar.
data class ProductsResponse(
    @SerializedName("products") val products: List<ProductModel>
)
