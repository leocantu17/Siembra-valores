package com.siembravalores.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.siembravalores.SiembraValoresScreen
import com.siembravalores.data.SiembraValoresUiState
import com.example.siembravalores.ui.theme.AccentGreen
import com.example.siembravalores.ui.theme.DarkGreen
import com.example.siembravalores.ui.theme.LightGreen
import com.example.siembravalores.ui.theme.MediumGreen
import com.example.siembravalores.ui.theme.SoftWhite
import com.example.siembravalores.ui.theme.VeryLightGreen
import com.siembravalores.R


@Composable
fun ChangePasswordScreen(
    nuevaContrasena: String,
    confirmarContrasena: String,
    uiState: SiembraValoresUiState,
    onValueChangeNuevaContrasena: (String) -> Unit,
    onValueChangeConfirmarContrasena: (String) -> Unit,
    onChangePasswordClicked: () -> Unit,
    navController: NavController
) {
    LaunchedEffect(key1 = uiState.contrasenaActualizada) {
        if (uiState.contrasenaActualizada) {
            navController.navigate(SiembraValoresScreen.Login.name) {
                popUpTo(SiembraValoresScreen.CambiarContrasena.name) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        VeryLightGreen,
                        SoftWhite,
                        LightGreen.copy(alpha = 0.3f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header card
            Card(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🔑",
                        fontSize = 48.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Nueva Contraseña",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = DarkGreen,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "¡Crea una contraseña segura! 🔐💪",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MediumGreen,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Change password form card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    EditPasswordField(
                        value = nuevaContrasena,
                        onValueChange = onValueChangeNuevaContrasena,
                        ruta = R.string.nueva_contrasena,
                        modifier = Modifier.fillMaxWidth()
                    )

                    EditPasswordField(
                        value = confirmarContrasena,
                        onValueChange = onValueChangeConfirmarContrasena,
                        ruta = R.string.confirmar_contrasena,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Password requirements
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = VeryLightGreen.copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "💡 Requisitos de contraseña:",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = DarkGreen,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "• Mínimo 8 caracteres\n• Al menos una mayúscula\n• Al menos un número\n• Un carácter especial",
                                style = MaterialTheme.typography.bodySmall,
                                color = MediumGreen
                            )
                        }
                    }

                    Button(
                        onClick = onChangePasswordClicked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkGreen,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                        enabled = nuevaContrasena.isNotEmpty() &&
                                confirmarContrasena.isNotEmpty() &&
                                nuevaContrasena == confirmarContrasena
                    ) {
                        Text(
                            text = "🌟 Actualizar Contraseña 🌟",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // Password match indicator
                    if (confirmarContrasena.isNotEmpty()) {
                        val passwordsMatch = nuevaContrasena == confirmarContrasena
                        Text(
                            text = if (passwordsMatch) "✅ Las contraseñas coinciden" else "❌ Las contraseñas no coinciden",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (passwordsMatch) AccentGreen else Color.Red,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Footer message
            Text(
                text = "¡Protege tu jardín digital! 🌳🔒",
                style = MaterialTheme.typography.bodyMedium,
                color = AccentGreen,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}