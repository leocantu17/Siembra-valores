package com.example.siembravalores

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.siembravalores.ui.AddServiceScreen
import com.example.siembravalores.ui.Arboles
import com.example.siembravalores.ui.HistorialServiciosApp
import com.example.siembravalores.ui.InicioScreen
import com.example.siembravalores.ui.LoginScreen
import com.example.siembravalores.ui.MisionesScreen
import com.example.siembravalores.ui.NotificacionesScreen
import com.example.siembravalores.ui.PantallaAdopcion
import com.example.siembravalores.ui.PerfilScreen
import com.example.siembravalores.ui.SiembraValoresViewModel
import com.example.siembravalores.ui.TreeList

enum class SiembraValoresScreen(@StringRes val title:Int){
    Inicio(title = R.string.inicio),
    Login(title = R.string.login),
    Arboles(title = R.string.misArboles),
    Adoptar(title = R.string.Adoptar),
    misArboles(title=R.string.misArboles),
    historial(title = R.string.historial),
    agregarServicio(title = R.string.agregarServicio),
    perfil(title = R.string.perfil),
    misiones(title = R.string.misiones),
    notificaciones(title = R.string.notificaciones)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiembraValoresAppBar(
    currentScreen: SiembraValoresScreen,
    canNavigateBack: Boolean,
    navigateUp:()->Unit={},
    modifier: Modifier=Modifier
){
    TopAppBar(
        title = {Text(stringResource(currentScreen.title))},
        modifier=modifier,
        navigationIcon = {
            if (canNavigateBack) {
            IconButton(onClick =  navigateUp ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
            }
        }
    )
}



@Composable
fun SiembraValoresApp(
    viewModel: SiembraValoresViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SiembraValoresScreen.valueOf(
        backStackEntry?.destination?.route ?: SiembraValoresScreen.Inicio.name
    )

    Scaffold(
        topBar = {
            SiembraValoresAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            val items = listOf(
                Items_menu.Adoptar,
                Items_menu.Historial,
                Items_menu.misArboles,
                Items_menu.Perfil,
                Items_menu.Notificaciones
            )
            val backStackEntry by navController.currentBackStackEntryAsState()
            NavigationBar {
                val currentRoute = backStackEntry?.destination?.route
                items.forEach { item ->
                    val selected = currentRoute == item.ruta

                    NavigationBarItem(selected = selected, onClick = {
                        navController.navigate(item.ruta) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }, icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
//                        label = { Text(text = item.title) },
//                        alwaysShowLabel = false
                    )
                }
            }

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SiembraValoresScreen.Inicio.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = SiembraValoresScreen.Inicio.name) {
                InicioScreen(
                    onNextButtonClicked = {
                        navController.navigate(SiembraValoresScreen.Login.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
            composable(route = SiembraValoresScreen.Login.name) {
                val uiState by viewModel.uiState.collectAsState()
                    LoginScreen(
                        correo = viewModel.correo,
                        contrasena = viewModel.contrasena,
                        uiState =uiState,
                        onValueChangeCorreo = { viewModel.updateCorreo(it) },
                        onValueChangeContrasena = { viewModel.updateContrasena(it) },
                        onNextButtonClicked = {
                            viewModel.autenticarUsuario(uiState.correo, uiState.contrasena)
                        },
                        navController
                    )
            }
            composable(route=SiembraValoresScreen.Arboles.name){
                Arboles(
                    onNextButtonClicked={
                        navController.navigate(SiembraValoresScreen.Adoptar.name)
                    }
                )
            }
            composable(route = SiembraValoresScreen.Adoptar.name) {
                PantallaAdopcion(
                    onNextButtonClicked={
                        navController.navigate(SiembraValoresScreen.misArboles.name)
                    }
                )
            }
            composable(route = SiembraValoresScreen.misArboles.name) {
                TreeList(
                    onNextButtonClicked={
                        navController.navigate(SiembraValoresScreen.agregarServicio.name)
                    }
                )
            }
            composable(route=SiembraValoresScreen.agregarServicio.name){
                AddServiceScreen(
                    onNextButtonClicked={
                        navController.navigate(SiembraValoresScreen.historial.name)
                    }
                )
            }
            composable(route = SiembraValoresScreen.historial.name) {
                HistorialServiciosApp(
                    onNextButtonClicked={
                        navController
                    }
                )
            }
            composable(route = SiembraValoresScreen.perfil.name) {
                PerfilScreen()
            }
            composable(route=SiembraValoresScreen.misiones.name){
                MisionesScreen()
            }
            composable(route = SiembraValoresScreen.notificaciones.name) {
                NotificacionesScreen()
            }
        }
    }
}
