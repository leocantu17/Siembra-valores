package com.example.siembravalores.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import com.example.siembravalores.R

@Composable
fun PerfilScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Espacio para la imagen de perfil
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Usa la imagen llamada Perfil.jpg
            contentDescription = "Imagen de perfil",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape), // Usamos CircleShape para recortar la imagen
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nombre de usuario y correo
        Text(
            text = "Usuario",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "juanitonoseque@gmail.com",
            fontSize = 14.sp,
            color = Color.Blue
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Información adicional del perfil
        ProfileInfo(label = "Fecha de nacimiento:", value = "07/12/2014")
        ProfileInfo(label = "Edad:", value = "10 años")
        ProfileInfo(label = "Fecha de registro:", value = "12/01/2023")
        ProfileInfo(label = "Celular:", value = "555-123-4567")
        ProfileInfo(label = "Escuela:", value = "Escuela Secundaria")

        Spacer(modifier = Modifier.weight(1f)) // Deja espacio para el navegador en la parte inferior

        // Línea de separación para el futuro navegador
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}

@Composable
fun ProfileInfo(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}
