package com.example.siembravalores

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.siembravalores.ui.AddServiceScreen
import com.example.siembravalores.ui.HistorialServiciosScreen
import com.example.siembravalores.ui.InicioScreen
import com.example.siembravalores.ui.LoginScreen
import com.example.siembravalores.ui.NotificacionesScreen
import com.example.siembravalores.ui.PerfilScreen
import com.example.siembravalores.ui.SiembraValoresViewModel
import com.example.siembravalores.ui.TreeList
import android.graphics.Bitmap
import com.example.siembravalores.ui.RecuperarContrasenaScreen

// Child-friendly green color palette for navigation
object NavigationTreeColors {
    val LightGreen = Color(0xFF81C784)
    val MediumGreen = Color(0xFF4CAF50)
    val DarkGreen = Color(0xFF2E7D32)
    val VeryLightGreen = Color(0xFFC8E6C9)
    val BackgroundGreen = Color(0xFFE8F5E8)
    val White = Color.White
    val DarkText = Color(0xFF1B5E20)
    val AccentGreen = Color(0xFF66BB6A)
    val SkyBlue = Color(0xFF87CEEB)
    val PureWhite = Color(0xFFFFFFF8)
}

enum class SiembraValoresScreen(@StringRes val title: Int, val emoji: String) {
    Inicio(title = R.string.inicio, emoji = "🌱"),
    Login(title = R.string.login, emoji = "🔐"),
    misArboles(title = R.string.misArboles, emoji = "🌳"),
    historial(title = R.string.historial, emoji = "📖"),
    agregarServicio(title = R.string.agregarServicio, emoji = "🌿"),
    perfil(title = R.string.perfil, emoji = "👤"),
    notificaciones(title = R.string.notificaciones, emoji = "🔔"),
    RecuperarContrasena(title=R.string.recuperar, emoji = "")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiembraValoresAppBar(
    currentScreen: SiembraValoresScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "${currentScreen.emoji} ${stringResource(currentScreen.title)}",
                color = NavigationTreeColors.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(NavigationTreeColors.White.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = NavigationTreeColors.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = NavigationTreeColors.MediumGreen
        )
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
            val uiState by viewModel.uiState.collectAsState()
            if (uiState.autenticado) { // Show only if authenticated
                val items = listOf(
                    Items_menu.misArboles,
                    Items_menu.Perfil,
                    Items_menu.Notificaciones
                )

                val backStackEntry by navController.currentBackStackEntryAsState()
                NavigationBar(
                    containerColor = NavigationTreeColors.White,
                    contentColor = NavigationTreeColors.DarkGreen,
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    NavigationTreeColors.VeryLightGreen,
                                    NavigationTreeColors.White
                                )
                            )
                        )
                ) {
                    val currentRoute = backStackEntry?.destination?.route
                    items.forEach { item ->
                        val selected = currentRoute == item.ruta

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.ruta) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = getNavigationItemDescription(item.ruta),
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (selected) NavigationTreeColors.LightGreen.copy(alpha = 0.3f)
                                            else Color.Transparent
                                        )
                                        .padding(4.dp),
                                    tint = if (selected) NavigationTreeColors.DarkGreen
                                    else NavigationTreeColors.MediumGreen
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = NavigationTreeColors.DarkGreen,
                                unselectedIconColor = NavigationTreeColors.MediumGreen,
                                indicatorColor = NavigationTreeColors.LightGreen.copy(alpha = 0.4f)
                            )
                        )
                    }
                }
            }
        },
        containerColor = NavigationTreeColors.BackgroundGreen
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SiembraValoresScreen.Inicio.name,
            modifier = Modifier
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            NavigationTreeColors.BackgroundGreen,
                            NavigationTreeColors.VeryLightGreen
                        )
                    )
                )
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
                    uiState = uiState,
                    onValueChangeCorreo = { viewModel.updateCorreo(it) },
                    onValueChangeContrasena = { viewModel.updateContrasena(it) },
                    onNextButtonClicked = {
                        viewModel.autenticarUsuario(uiState.correo, uiState.contrasena)
                    },
                    navController
                )
            }
            composable(route = SiembraValoresScreen.RecuperarContrasena.name) {
                val uiState by viewModel.uiState.collectAsState()
                RecuperarContrasenaScreen(
                    correo = uiState.correoRecuperacion,
                    onValueChangeCorreo = { viewModel.updateCorreoRec(it) },
                    onRecuperarButtonClicked = { viewModel.RecuperarContrasena(uiState.correoRecuperacion) },
                    navController = navController
                )
            }
            composable(route = SiembraValoresScreen.misArboles.name) {
                val uiState by viewModel.uiState.collectAsState()
                TreeList(
                    consultaArboles = { viewModel.misArboles(uiState.id_Us) },
                    consultaArbolesInfo = { viewModel.misArbolesInfo(it) },
                    onNextButtonClicked = {
                        viewModel.actualizarIDArbol(it)
                        navController.navigate(SiembraValoresScreen.agregarServicio.name)
                    },
                    onNextButtonHistorial = {
                        viewModel.actualizarIDArbol(it)
                        navController.navigate(SiembraValoresScreen.historial.name)
                    },
                    uiState = uiState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            composable(route = SiembraValoresScreen.agregarServicio.name) {
                val uiState by viewModel.uiState.collectAsState()
                AddServiceScreen(
                    onNextButtonClicked = {
                        navController.navigate(SiembraValoresScreen.historial.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    consulta = { viewModel.obtenerServicios() },
                    uiState = uiState,
                    onSelectionChange = { viewModel.setServicio(it) },
                    // Cambiar firma para incluir Bitmap? como último parámetro
                    onServiceDetailsSubmit = { comentarios: String, altura: Float, circunferencia: Float, imagenBitmap: Bitmap? ->
                        if (imagenBitmap != null) {
                            viewModel.agregarServicioDetalles(
                                comentarios,
                                altura,
                                circunferencia,
                                imagenBitmap
                            )
                        } else {
                            // Llamar sin imagen o manejar el caso
                            viewModel.agregarServicioDetalles(
                                comentarios,
                                altura,
                                circunferencia,
                                null
                            )
                        }
                    }

                )
            }

            composable(route = SiembraValoresScreen.historial.name) {
                val uiState by viewModel.uiState.collectAsState()
                HistorialServiciosScreen(
                    uiState = uiState,
                    onConsulta = { viewModel.historialServicios() }
                )
            }

            composable(route = SiembraValoresScreen.perfil.name) {
                val uiState by viewModel.uiState.collectAsState()
                PerfilScreen(
                    consulta = { viewModel.obtenerPerfil() },
                    uiState = uiState
                )
            }

            composable(route = SiembraValoresScreen.notificaciones.name) {
                val uiState by viewModel.uiState.collectAsState()
                NotificacionesScreen(
                    notificaciones = uiState.notificaciones,
                    uiState = uiState,
                    onNotificationClick = {
                        viewModel.notificacionLeida(it)
                    }
                )
            }
        }
    }
}

// Helper function to get descriptive text for navigation items
private fun getNavigationItemDescription(route: String): String {
    return when (route) {
        "misArboles" -> "🌳 Mis Árboles"
        "perfil" -> "👤 Mi Perfil"
        "notificaciones" -> "🔔 Notificaciones"
        else -> "Navegación"
    }
}