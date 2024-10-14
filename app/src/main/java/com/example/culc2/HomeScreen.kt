package com.example.culc2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Розрахунку валових викидів шкідливих речовин")

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка для переходу на екран Вугілля
        Button(
            modifier = Modifier.width(200.dp),
            onClick = { navController.navigate("coal") } // Навігація на екран Вугілля
        ) {
            Text(
                text = "Вугілля",
                fontSize = 30.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.width(200.dp),
            onClick = { navController.navigate("mazut") }
        ) {
            Text(
                text = "Мазут",
                fontSize = 30.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .shadow(8.dp, shape = RoundedCornerShape(15.dp))
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(Color.Blue),
            onClick = { navController.navigate("gas") }
        ) {
            Text(
                text = "Газ",
                fontSize = 30.sp
            )
        }
    }
}
