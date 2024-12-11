package com.example.siembravalores.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.siembravalores.R


@Composable
fun MisionesScreen() {

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo del árbol
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Reemplaza con tu recurso
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 16.dp)
            )

            // Score
            Text(
                text = "SCORE: 0000",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Línea divisoria
            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            // Título de misiones
            Text(
                text = "MISSIONS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF880808),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Lista de misiones
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                MissionItem("Riega el árbol", "10 pts")
                MissionItem("Mata al árbol", "-100 pts")
                MissionItem("Fertiliza el árbol", "10 pts")
                MissionItem("Podar", "15 pts")
            }
        }
    }


@Composable
fun MissionItem(title: String, points: String) {
    // Estado para el Checkbox
    val isChecked = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Texto de la misión
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = points,
                fontSize = 14.sp,
                color = if (points.contains("-")) Color.Red else Color.Black
            )
        }

        // Checkbox para marcar la misión como completada
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it },
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}