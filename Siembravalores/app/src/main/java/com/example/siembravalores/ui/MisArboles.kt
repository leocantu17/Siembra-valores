package com.example.siembravalores.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
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

data class Tree(val name: String, val description: String, val scientificName: String, val age: Int, val location: String, val characteristics: String, val careRoutine: String, val imageResId: Int)



@Composable
fun TreeList(modifier: Modifier = Modifier) {
    val trees = listOf(
        Tree("Palo de rosa", "Un árbol tropical con un fuerte olor dulce", "Dalbergia retusa", 10, "20.1234, -99.1234", "Madera fuerte y duradera", "Riego semanal", R.drawable.facebook),
        Tree("Roble", "Árbol grande con hojas resistentes", "Quercus robur", 15, "21.2345, -98.2345", "Madera dura", "Riego mensual", R.drawable.facebook),
        Tree("Sauce llorón", "Árbol con ramas largas que se doblan hacia abajo", "Salix babylonica", 7, "22.3456, -97.3456", "Corteza flexible", "Riego frecuente", R.drawable.facebook)
    )

    LazyColumn(modifier = modifier) {
        items(trees.size) { index ->
            TreeItem(tree = trees[index])
        }
    }
}

@Composable
fun TreeItem(tree: Tree) {
    var expanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { expanded.value = !expanded.value },
        horizontalAlignment = Alignment.Start
    ) {
        // Título, imagen y descripción del árbol
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = tree.imageResId),
                contentDescription = "Imagen del árbol",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tree.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = tree.scientificName,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
            // Flecha de expansión
            Icon(
                painter = painterResource(id = R.drawable.logo), // Cambié al nombre correcto
                contentDescription = "Expandir",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Text(
            text = tree.description,
            fontSize = 14.sp,
            color = Color.Gray
        )

        // Si el item está expandido, muestra más información
        if (expanded.value) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Text("Edad: ${tree.age} años")
                Text("Ubicación: ${tree.location}")
                Text("Características: ${tree.characteristics}")
                Text("Rutina de cuidado: ${tree.careRoutine}")
            }
        }
    }
}

