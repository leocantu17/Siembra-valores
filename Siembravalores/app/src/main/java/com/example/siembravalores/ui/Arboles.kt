package com.example.siembravalores.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.siembravalores.R

@Composable
fun Arboles(onNextButtonClicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Encabezado(onNextButtonClicked)
        ListaDeArboles(onNextButtonClicked)
        EspacioParaExplorador()
    }
}

@Composable
fun Encabezado(onNextButtonClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(36.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Recurso existente
            contentDescription = "Imagen del Árbol",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Palo de rosa",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = "Tienen un fuerte olor dulce, que persiste durante años.",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(onClick = onNextButtonClicked) {
            Text(text = "Adoptar")
        }
    }
}

@Composable
fun ListaDeArboles(onNextButtonClicked: () -> Unit) {
    val arboles = listOf(
        "Árbol 1 - Descripción breve",
        "Árbol 2 - Descripción breve",
        "Árbol 3 - Descripción breve",
        "Árbol 4 - Descripción breve",
        "Árbol 5 - Descripción breve",
        "Árbol 6 - Descripción breve"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(arboles) { arbol ->
            TarjetaDeArbolGrid(arbol,onNextButtonClicked)
        }
    }
}

@Composable
fun TarjetaDeArbolGrid(nombre: String, onNextButtonClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp) // Elevación ajustada a Material 3
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Imagen del Árbol",
                modifier = Modifier.size(60.dp)
            )

            Text(
                text = nombre,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Button(
                onClick = onNextButtonClicked,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(text = "Adoptar", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun EspacioParaExplorador() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Espacio reservado para el explorador",
            color = Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light
        )
    }
}