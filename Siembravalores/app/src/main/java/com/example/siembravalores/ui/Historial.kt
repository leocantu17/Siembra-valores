package com.example.siembravalores.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection

//@Composable
//fun HistorialServiciosCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                painter = painterResource(id = affirmation.imagen),
//                contentDescription = stringResource(id = R.string.imagen),
//                modifier = Modifier.size(80.dp)
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Column {
//                Text(
//                    text = LocalContext.current.getString(affirmation.fechaPublicacion),
//                    modifier = Modifier.padding(16.dp),
//                    style = MaterialTheme.typography.headlineSmall
//                )
//                Text(
//                    text = LocalContext.current.getString(affirmation.comentarios),
//                    modifier = Modifier.padding(16.dp),
//                    style = MaterialTheme.typography.headlineSmall
//                )
//            }
//        }
//    }
//}Practica 6

@Composable
fun HistorialServicios(){
    Text(text = "Hola")
}

//@Composable
//fun HistorialServiciosList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {
//    LazyColumn(modifier = modifier) {
//        items(affirmationList) { affirmation ->
//            HistorialServiciosCard(
//                affirmation = affirmation,
//                modifier = Modifier.padding(8.dp)
//            )
//        }
//    }
//}

//@Composable
//fun HistorialServiciosApp(name: String, modifier: Modifier) {
//    val layoutDirection = LocalLayoutDirection.current
//    Surface(
//        modifier = Modifier
//            .fillMaxSize()
//            .statusBarsPadding()
//            .padding(
//                start = WindowInsets.safeDrawing
//                    .asPaddingValues()
//                    .calculateStartPadding(layoutDirection),
//                end = WindowInsets.safeDrawing
//                    .asPaddingValues()
//                    .calculateEndPadding(layoutDirection),
//            ),
//    ) {
//        HistorialServiciosList(
//            affirmationList = Datasource().loadAffirmations(),
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun HistorialServiciosPreview() {
//    Practica6Theme {
//        HistorialServiciosCard(
//            Affirmation(
//                R.string.comentarios1,
//                R.drawable.podar,
//                R.string.fecha1
//            )
//        )
//
//    }
//}
