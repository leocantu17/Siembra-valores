package com.siembravalores.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.siembravalores.R
import com.siembravalores.SiembraValoresScreen
import com.siembravalores.data.SiembraValoresUiState
import com.example.siembravalores.ui.theme.AccentGreen
import com.example.siembravalores.ui.theme.DarkGreen
import com.example.siembravalores.ui.theme.LightGreen
import com.example.siembravalores.ui.theme.MediumGreen
import com.example.siembravalores.ui.theme.SoftWhite
import com.example.siembravalores.ui.theme.VeryLightGreen

@Composable
fun LoginScreen(
    correo: String,
    contrasena: String,
    uiState: SiembraValoresUiState,
    onValueChangeCorreo: (String) -> Unit,
    onValueChangeContrasena: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Manejo de navegación basado en el estado de autenticación
    NavigationHandler(uiState, navController)

    LoginContent(
        correo = correo,
        contrasena = contrasena,
        uiState = uiState,
        onValueChangeCorreo = onValueChangeCorreo,
        onValueChangeContrasena = onValueChangeContrasena,
        onNextButtonClicked = onNextButtonClicked,
        navController = navController,
        modifier = modifier
    )
}

@Composable
private fun NavigationHandler(
    uiState: SiembraValoresUiState,
    navController: NavController
) {
    val usuario = uiState.usuario.firstOrNull()


    if (usuario?.FECHA_CON != null && uiState.autenticado) {
        LaunchedEffect(Unit) {
            navController.navigate(SiembraValoresScreen.misArboles.name) {
                popUpTo(SiembraValoresScreen.Login.name) { inclusive = true }
            }
        }
    } else if (uiState.noIngresadoAntes == true) {
        LaunchedEffect(Unit) {
            navController.navigate(SiembraValoresScreen.CodigoVerificacion.name)
        }
    }
}

@Composable
private fun LoginContent(
    correo: String,
    contrasena: String,
    uiState: SiembraValoresUiState,
    onValueChangeCorreo: (String) -> Unit,
    onValueChangeContrasena: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
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

            WelcomeHeader()
            LoginFormCard(
                correo = correo,
                contrasena = contrasena,
                isLoading = uiState.isLoading,
                onValueChangeCorreo = onValueChangeCorreo,
                onValueChangeContrasena = onValueChangeContrasena,
                onNextButtonClicked = onNextButtonClicked,
                navController = navController
            )
            FooterMessage()
        }
    }
}

@Composable
private fun WelcomeHeader() {
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
                text = "🌳",
                fontSize = 48.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.registro),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                textAlign = TextAlign.Center
            )
            Text(
                text = "¡Cuida tu árbol especial! 🌱",
                style = MaterialTheme.typography.bodyLarge,
                color = MediumGreen,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun LoginFormCard(
    correo: String,
    contrasena: String,
    isLoading: Boolean,
    onValueChangeCorreo: (String) -> Unit,
    onValueChangeContrasena: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
    navController: NavController
) {
    val focusManager = LocalFocusManager.current
    val isFormValid = correo.isNotBlank() && contrasena.isNotBlank()

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
            EmailTextField(
                value = correo,
                onValueChange = onValueChangeCorreo,
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                modifier = Modifier.fillMaxWidth()
            )

            PasswordTextField(
                value = contrasena,
                onValueChange = onValueChangeContrasena,
                onDone = { if (isFormValid) onNextButtonClicked() },
                modifier = Modifier.fillMaxWidth()
            )

            ForgotPasswordLink(navController)

            LoginButton(
                onClick = onNextButtonClicked,
                isLoading = isLoading,
                isEnabled = isFormValid && !isLoading,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = {
            Text(
                stringResource(R.string.correo),
                color = MediumGreen,
                fontWeight = FontWeight.Medium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = MediumGreen
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext() }
        ),
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

@Composable
private fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = {
            Text(
                stringResource(R.string.contrasena),
                color = MediumGreen,
                fontWeight = FontWeight.Medium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = MediumGreen
            )
        },
        trailingIcon = {
            PasswordVisibilityToggle(
                passwordVisible = passwordVisible,
                onToggle = { passwordVisible = !passwordVisible }
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
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

@Composable
private fun PasswordVisibilityToggle(
    passwordVisible: Boolean,
    onToggle: () -> Unit
) {
    val image = if (passwordVisible) R.drawable.visible else R.drawable.invisible
    val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

    IconButton(onClick = onToggle) {
        Icon(
            painter = painterResource(id = image),
            contentDescription = description,
            tint = MediumGreen
        )
    }
}

@Composable
private fun ForgotPasswordLink(navController: NavController) {
    Text(
        text = "¿Olvidaste tu contraseña? 🔑",
        style = MaterialTheme.typography.bodyMedium,
        color = MediumGreen,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .clickable {
                navController.navigate(SiembraValoresScreen.RecuperarContrasena.name)
            }
            .padding(vertical = 4.dp)
    )
}

@Composable
private fun LoginButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkGreen,
            contentColor = Color.White,
            disabledContainerColor = MediumGreen.copy(alpha = 0.6f)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            Text(
                text = "🌟 ${stringResource(id = R.string.enviar)} 🌟",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun FooterMessage() {
    Text(
        text = "¡Vamos a plantar semillas de valores! 🌱💚",
        style = MaterialTheme.typography.bodyMedium,
        color = AccentGreen,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 24.dp)
    )
}

// Composables reutilizables para otros formularios
@Composable
fun EditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes ruta: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = {
            Text(
                stringResource(ruta),
                color = MediumGreen,
                fontWeight = FontWeight.Medium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MediumGreen
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() },
            onDone = { onImeAction() }
        ),
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

@Composable
fun EditPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes ruta: Int,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = {
            Text(
                stringResource(ruta),
                color = MediumGreen,
                fontWeight = FontWeight.Medium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = MediumGreen
            )
        },
        trailingIcon = {
            PasswordVisibilityToggle(
                passwordVisible = passwordVisible,
                onToggle = { passwordVisible = !passwordVisible }
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() },
            onDone = { onImeAction() }
        ),
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
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