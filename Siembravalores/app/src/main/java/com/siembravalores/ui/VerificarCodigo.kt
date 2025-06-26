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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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

@Composable
fun VerificationCodeScreen(
    codigoVerificacion: String,
    uiState: SiembraValoresUiState,
    onValueChangeCodigoVerificacion: (String) -> Unit,
    onVerifyButtonClicked: () -> Unit,
    navController: NavController
) {
    LaunchedEffect(uiState.codigoVerificado) {
        if (uiState.codigoVerificado) {
            navController.navigate(SiembraValoresScreen.CambiarContrasena.name) {
                popUpTo(SiembraValoresScreen.CodigoVerificacion.name) { inclusive = true }
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
                        text = "🔐",
                        fontSize = 48.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Código de Verificación",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = DarkGreen,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "¡Revisa tu correo! 📧✨",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MediumGreen,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            // Verification form card
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
                    Text(
                        text = "Ingresa el código de 6 dígitos que enviamos a tu correo electrónico",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MediumGreen,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    EditVerificationField(
                        value = codigoVerificacion,
                        onValueChange = onValueChangeCodigoVerificacion,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = onVerifyButtonClicked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkGreen,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text(
                            text = "🔓 Verificar Código 🔓",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }


            // Footer message
            Text(
                text = "¡Tu seguridad es nuestra prioridad! 🛡️💚",
                style = MaterialTheme.typography.bodyMedium,
                color = AccentGreen,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}


@Composable
fun EditVerificationField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Limitar a 6 dígitos
            if (newValue.length <= 6 && newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        singleLine = true,
        label = {
            Text(
                "Código de verificación",
                color = MediumGreen,
                fontWeight = FontWeight.Medium
            )
        },
        placeholder = {
            Text(
                "123456",
                color = MediumGreen.copy(alpha = 0.5f)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DarkGreen,
            unfocusedBorderColor = LightGreen,
            focusedLabelColor = DarkGreen,
            unfocusedLabelColor = MediumGreen,
            cursorColor = DarkGreen,
            focusedTextColor = AccentGreen,
            unfocusedTextColor = AccentGreen
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    )
}