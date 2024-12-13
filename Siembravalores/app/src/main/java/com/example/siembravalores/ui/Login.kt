package com.example.siembravalores.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.siembravalores.R
import com.example.siembravalores.SiembraValoresScreen
import com.example.siembravalores.data.SiembraValoresUiState

@Composable
fun LoginScreen(
    correo: String,
    contrasena: String,
    uiState: SiembraValoresUiState,
    onValueChangeCorreo: (String) -> Unit,
    onValueChangeContrasena: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
    navController: NavController
) {
    Text(text = "${uiState.error}", color = Color.Black) // Texto negro
    LaunchedEffect(key1 = uiState.autenticado) {
        if (uiState.autenticado) {
            navController.navigate(SiembraValoresScreen.Arboles.name) {
                popUpTo(SiembraValoresScreen.Login.name) { inclusive = true }
            }
        }
    }
    Column(
        modifier = Modifier
            .background(Color.White) // Fondo blanco
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 40.dp)
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.registro),
                color = Color.Black, // Texto negro
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 40.dp)
                    .align(alignment = Alignment.Start)
            )

            EditTextField(
                value = correo,
                onValueChange = onValueChangeCorreo,
                ruta = R.string.correo,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            EditPasswordField(
                value = contrasena,
                onValueChange = onValueChangeContrasena,
                ruta = R.string.contrasena,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = onNextButtonClicked,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.enviar),
                    color = Color.Black // Texto negro
                )
            }
        }
    }
}

@Composable
fun EditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes ruta: Int,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(stringResource(ruta), color = Color.Black) }, // Texto negro
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = modifier
    )
}

@Composable
fun EditPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes ruta: Int,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(stringResource(ruta), color = Color.Black) }, // Texto negro
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(), // Transformación de contraseña
        modifier = modifier
    )
}
