package com.example.siembravalores.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

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
fun HistorialServiciosApp(onNextButtonClicked: () -> NavHostController) {
    Text(text = "Modulo en matenimiento")
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
