package com.example.culc2


import android.app.DownloadManager.Query
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MazutScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Це екран розрахунку викидів для Вугілля")

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка для повернення назад
        Button(
            onClick = { navController.popBackStack() } // Повернутися на головний екран
        ) {
            Text(text = "Назад")
        }

        Calculate()
    }
}


@Composable
fun Calculate() {

    // Категорії вугілля
    val categories = listOf(
        "Високосірчастий 40",
        "Високосірчастий 100",
        "Високосірчастий 200",
        "Малосірчастий 40",
        "Малосірчастий 100"
    )

// Стан для обраної категорії
    var selectedCategory by remember { mutableStateOf(categories[0]) }

// Змінні для розрахунків
    var A by remember { mutableStateOf(0.0) }
    var Q by remember { mutableStateOf(0.0) }
    var W by remember { mutableStateOf(0.0) }

// Вхідне значення маси
    var fuelMass by remember { mutableStateOf("") }

// Оновлення змінних залежно від вибраної категорії
    when (selectedCategory) {
        "Високосірчастий 40" -> {
            A = 0.15
            Q = 40.40
            W = 2.00
        }
        "Високосірчастий 100" -> {
            A = 0.15
            Q = 40.03
            W = 2.00
        }
        "Високосірчастий 200" -> {
            A = 0.30
            Q = 39.77
            W = 1.00
        }
        "Малосірчастий 40" -> {
            A = 0.15
            Q = 41.24
            W = 2.00
        }
        "Малосірчастий 100" -> {
            A = 0.15
            Q = 40.82
            W = 2.00
        }
    }

    // Функція для розрахунку валового викиду
    fun calculateEmission(a: Double, q: Double, fuelMass: Double): Pair<Double, Double> {
        val first = (1000000 / q) * 1
        val second = (a / 100) * (1 - 0.985)
        val k = first * second
        val E = 0.000001 * k * q * fuelMass
        return Pair(k, E)
    }

// Оновлення результату розрахунку після зміни маси палива
    val (k, result) = if (fuelMass.isNotEmpty()) {
        val mass = fuelMass.toDoubleOrNull() ?: 0.0
        calculateEmission(A, Q, mass)
    } else {
        0.0 to 0.0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Dropdown для вибору категорії
        var expanded by remember { mutableStateOf(false) }

        Box {
            Button(onClick = { expanded = true }) {
                Text(text = "Виберіть тип вугілля: $selectedCategory")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Вхід для введення маси палива
        TextField(
            value = fuelMass,
            onValueChange = { fuelMass = it },
            label = { Text("Маса палива (т)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Виведення результату розрахунку
        Text(text = "Коефіцієнт k: %.2f".format(k))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Результат валового викиду для $selectedCategory: %.2f т".format(result))
    }



}