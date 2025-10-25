package com.example.lab09products

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size

@Composable
fun ScreenProducts(navController: NavHostController, servicio: ProductApiService) {
    var productList by remember { mutableStateOf<List<ProductModel>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Cargar los productos del servicio API
    LaunchedEffect(Unit) {
        try {
            productList = servicio.getProducts().products
            errorMessage = null
        } catch (e: Exception) {
            // Evitar crash si hay error de red o parseo
            errorMessage = e.localizedMessage ?: "Error desconocido al cargar productos"
            productList = emptyList()
        }
    }

    // Mostrar error si ocurre
    errorMessage?.let { msg ->
        Text(
            text = "Error al cargar productos: $msg",
            color = Color.Red,
            modifier = Modifier.padding(8.dp)
        )
    }

    // Lista de productos
    LazyColumn {
        items(productList) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { navController.navigate("productDetail/${product.id}") }
            ) {
                Row(Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(product.thumbnail),
                        contentDescription = product.title,
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(product.title, fontWeight = FontWeight.Bold)
                        Text("S/. ${product.price}")
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenProductDetail(servicio: ProductApiService, id: Int) {
    var product by remember { mutableStateOf<ProductModel?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Obtener el producto según su ID
    LaunchedEffect(Unit) {
        try {
            product = servicio.getProductById(id)
            errorMessage = null
        } catch (e: Exception) {
            errorMessage = e.localizedMessage ?: "Error desconocido al cargar detalle"
            product = null
        }
    }

    // Mostrar detalle del producto o error
    errorMessage?.let { msg ->
        Text(
            text = "Error al cargar detalle: $msg",
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    }

    product?.let {
        Column(Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(it.thumbnail),
                contentDescription = it.title,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
            Text(it.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("S/. ${it.price}", color = Color.Gray, fontSize = 16.sp)
            Spacer(Modifier.height(10.dp))
            Text(it.description)
        }
    }
}
