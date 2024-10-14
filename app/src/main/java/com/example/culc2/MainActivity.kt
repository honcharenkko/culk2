package com.example.culc2


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.culc2.ui.theme.Culc2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Culc2Theme {
                // Створюємо навігаційний контролер та ініціалізуємо навігацію
                val navController = rememberNavController()
                Navigation(navController)
            }
        }
    }
}



@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        // Головний екран
        composable("home") { HomeScreen(navController) }
        // Екран для Вугілля
        composable("coal") { CoalScreen(navController) }

        composable("mazut") { MazutScreen(navController) }

        composable("gas") { GasScreen(navController) }
    }
}
