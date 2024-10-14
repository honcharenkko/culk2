package com.example.culc2
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CoalScreen(navController: NavController) {
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

        CategorySelectionScreen()
    }
}

@Composable
fun CategorySelectionScreen() {
        // Категорії вугілля
        val categories = listOf(
            "Антрацитовий штиб АШ",
            "Пісне вугілля ТР",
            "Донецьке газове ГР",
            "Донецьке довгополуменеве ДР",
            "Львівсько-волинське (ЛВ) ГР",
            "Олександрійське буре БІР"
        )

        // Стан для обраної категорії
        var selectedCategory by remember { mutableStateOf(categories[0]) }

        // Змінні для розрахунків
        var A by remember { mutableStateOf(0.0) }
        var W by remember { mutableStateOf(0.0) }
        var Q by remember { mutableStateOf(0.0) }

        // Вхідне значення маси
        var fuelMass by remember { mutableStateOf("") }

        // Оновлення змінних залежно від вибраної категорії
        when (selectedCategory) {
            "Антрацитовий штиб АШ" -> {
                A = 5.0
                W = 3.0
                Q = 33.24
            }
            "Пісне вугілля ТР" -> {
                A = 12.0
                W = 6.0
                Q = 34.29
            }
            "Донецьке газове ГР" -> {
                A = 25.20
                W = 10.0
                Q = 31.98
            }
            "Донецьке довгополуменеве ДР" -> {
                A = 35.0
                W = 15.0
                Q = 30.56
            }
            "Львівсько-волинське (ЛВ) ГР" -> {
                A = 18.0
                W = 10.0
                Q = 31.69
            }
            "Олександрійське буре БІР" -> {
                A = 45.0
                W = 25.0
                Q = 26.96
            }
        }

        // Функція для розрахунку валового викиду
        fun calculateEmission(a: Double, w: Double, q: Double, fuelMass: Double): Pair<Double, Double> {
            val qr = q*(1-((w+a)/100))
            val first = (1000000 / qr) * 0.8
            val second = (a /(100-1.5)) * (1 - 0.985)
            val k = first * second
            val E = 0.000001 * k * qr * fuelMass
            return Pair(k, E)
        }

        // Оновлення результату розрахунку після зміни маси палива
        val (k, result) = if (fuelMass.isNotEmpty()) {
            val mass = fuelMass.toDoubleOrNull() ?: 0.0
            calculateEmission(A, W, Q, mass)
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
                label = { Text("Маса палива (т)")} ,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Виведення результату розрахунку
            Text(text = "Коефіцієнт k: %.2f".format(k))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Результат валового викиду для $selectedCategory: %.2f т".format(result))
        }


}

