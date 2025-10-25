package com.example.lab09products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab09products.ui.theme.Lab09ProductsTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab09ProductsTheme {
                MainApp()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MainApp() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val servicio = retrofit.create(ProductApiService::class.java)
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        content = { padding -> Content(padding, navController, servicio) }
    )
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Productos - DummyJSON",
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun Content(pv: PaddingValues, navController: NavHostController, servicio: ProductApiService) {
    NavHost(navController, startDestination = "products", Modifier.padding(pv)) {
        composable("products") { ScreenProducts(navController, servicio) }
        composable(
            "productDetail/{id}",
            arguments = listOf(navArgument("id") { type = androidx.navigation.NavType.IntType })
        ) {
            ScreenProductDetail(servicio, it.arguments!!.getInt("id"))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainApp() {
    Lab09ProductsTheme {
        MainApp()
    }
}
