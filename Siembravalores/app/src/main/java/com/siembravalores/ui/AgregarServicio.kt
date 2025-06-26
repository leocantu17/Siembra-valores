package com.siembravalores.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.siembravalores.data.SiembraValoresUiState
import com.example.siembravalores.ui.theme.TreeColors
import android.Manifest
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.BitmapFactory
import androidx.compose.runtime.saveable.Saver
import java.io.ByteArrayOutputStream
import java.io.IOException
import android.util.Base64
import androidx.core.graphics.scale
import kotlin.math.sqrt

@Composable
fun AddServiceScreen(
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    consulta: () -> Unit,
    uiState: SiembraValoresUiState,
    onSelectionChange: (Int) -> Unit,
    onServiceDetailsSubmit: (String, Float, Float, Bitmap?) -> Unit
) {
    // Launch the initial query when the composable is first created
    LaunchedEffect(key1 = true) {
        consulta()
    }

    // State variables for input fields - usando rememberSaveable para persistir estado
    var comments by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var circumference by rememberSaveable { mutableStateOf("") }
    var selectedServicioId by rememberSaveable { mutableStateOf<Int?>(null) }

    // SOLUCIÓN 1: Estado para la imagen con Saver personalizado para persistir durante recreaciones
    var imagenBitmap by rememberSaveable(BitmapSaver) { mutableStateOf<Bitmap?>(null) }

    // Scroll state
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TreeColors.BackgroundGreen,
                        TreeColors.VeryLightGreen
                    )
                )
            )
            .padding(20.dp)
            .verticalScroll(scrollState)
    ) {
        // Title with fun styling
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(containerColor = TreeColors.MediumGreen),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "🌱 ¡Elige un Servicio para tu Árbol! 🌳",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TreeColors.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        // Service selection with fun cards
        Text(
            text = "Servicios Disponibles:",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = TreeColors.DarkText,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        uiState.servicios.forEach { servicio ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .selectable(
                        selected = selectedServicioId == servicio.ID_SERVICIO,
                        onClick = {
                            selectedServicioId = servicio.ID_SERVICIO
                            onSelectionChange(servicio.ID_SERVICIO)
                        }
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedServicioId == servicio.ID_SERVICIO)
                        TreeColors.LightGreen
                    else
                        TreeColors.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedServicioId == servicio.ID_SERVICIO,
                        onClick = {
                            selectedServicioId = servicio.ID_SERVICIO
                            onSelectionChange(servicio.ID_SERVICIO)
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = TreeColors.DarkGreen,
                            unselectedColor = TreeColors.MediumGreen
                        )
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "🌿 ${servicio.TIPO ?: "Servicio especial"}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = TreeColors.DarkText
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Comments section with fun design
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = TreeColors.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "💭 Comentarios sobre tu árbol:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TreeColors.DarkText,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                BasicTextField(
                    value = comments,
                    onValueChange = { comments = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(TreeColors.VeryLightGreen)
                        .padding(12.dp),
                    keyboardOptions = KeyboardOptions.Default,
                    textStyle = LocalTextStyle.current.copy(
                        color = TreeColors.DarkText,
                        fontSize = 14.sp
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopStart
                        ) {
                            if (comments.isEmpty()) {
                                Text(
                                    text = "Cuéntanos algo especial sobre tu árbol...",
                                    color = TreeColors.DarkText.copy(alpha = 0.6f),
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Conditional rendering for specific service (ID 4) with fun design
        if (uiState.id_servicio == 4) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = TreeColors.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "📏 Medidas de tu árbol:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TreeColors.DarkText,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Height input
                    OutlinedTextField(
                        value = height,
                        onValueChange = { height = it },
                        label = {
                            Text(
                                "🌲 Altura (metros)",
                                color = TreeColors.DarkText
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TreeColors.MediumGreen,
                            unfocusedBorderColor = TreeColors.LightGreen,
                            focusedTextColor = TreeColors.DarkText,
                            unfocusedTextColor = TreeColors.DarkText
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Circumference input
                    OutlinedTextField(
                        value = circumference,
                        onValueChange = { circumference = it },
                        label = {
                            Text(
                                "⭕ Circunferencia (cm)",
                                color = TreeColors.DarkText
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TreeColors.MediumGreen,
                            unfocusedBorderColor = TreeColors.LightGreen,
                            focusedTextColor = TreeColors.DarkText,
                            unfocusedTextColor = TreeColors.DarkText
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Componente mejorado para tomar fotos con estado persistente
            TomarFotoMejorado(
                imagenBitmap = imagenBitmap,
                onImagenBitmapChange = { nuevaImagen ->
                    imagenBitmap = nuevaImagen
                }
            )
        }

        // Submit button with fun design
        Button(
            onClick = {
                onServiceDetailsSubmit(
                    comments,
                    height.toFloatOrNull() ?: 0f,
                    circumference.toFloatOrNull() ?: 0f,
                    imagenBitmap
                )
                onNextButtonClicked()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
                .height(56.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(
                containerColor = TreeColors.MediumGreen
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Agregar servicio",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TreeColors.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun TomarFotoMejorado(
    imagenBitmap: Bitmap?,
    onImagenBitmapChange: (Bitmap?) -> Unit
) {
    val context = LocalContext.current

    // Estado para manejo de errores - usando remember normal ya que no necesita persistir
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // SOLUCIÓN 2: URI temporal para la imagen usando rememberSaveable para persistir
    var photoUri by rememberSaveable { mutableStateOf<String?>(null) }

    // Función para crear un archivo temporal
    fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_${timeStamp}_"
        val storageDir = File(context.cacheDir, "images")
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    // SOLUCIÓN 3: Launcher para la cámara con manejo mejorado de errores
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoUri != null) {
            try {
                val uri = Uri.parse(photoUri!!)
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                if (bitmap != null) {
                    // Redimensionar si es muy grande para evitar OutOfMemoryError
                    val scaledBitmap = if (bitmap.width > 1024 || bitmap.height > 1024) {
                        val ratio = minOf(1024.0 / bitmap.width, 1024.0 / bitmap.height)
                        Bitmap.createScaledBitmap(
                            bitmap,
                            (bitmap.width * ratio).toInt(),
                            (bitmap.height * ratio).toInt(),
                            true
                        )
                    } else {
                        bitmap
                    }

                    onImagenBitmapChange(scaledBitmap)

                    // Limpiar la URI temporal después de usar
                    photoUri = null
                } else {
                    errorMessage = "Error al procesar la imagen"
                    showErrorDialog = true
                }
            } catch (e: IOException) {
                errorMessage = "Error al leer la imagen: ${e.message}"
                showErrorDialog = true
            } catch (e: Exception) {
                errorMessage = "Error inesperado: ${e.message}"
                showErrorDialog = true
            }
        } else {
            if (!success) {
                // El usuario canceló la captura, no mostrar error
                photoUri = null
            } else {
                errorMessage = "No se pudo capturar la imagen"
                showErrorDialog = true
            }
        }
    }

    // Launcher para permisos
    val permisoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            try {
                val photoFile = createImageFile()
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    photoFile
                )
                photoUri = uri.toString()
                cameraLauncher.launch(uri)
            } catch (e: Exception) {
                errorMessage = "Error al crear archivo temporal: ${e.message}"
                showErrorDialog = true
            }
        } else {
            errorMessage = "Se necesita permiso de cámara para tomar fotos"
            showErrorDialog = true
        }
    }

    // UI
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = TreeColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "📷 Fotografía del árbol:",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = TreeColors.DarkText,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) -> {
                            try {
                                val photoFile = createImageFile()
                                val uri = FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.fileprovider",
                                    photoFile
                                )
                                photoUri = uri.toString()
                                cameraLauncher.launch(uri)
                            } catch (e: Exception) {
                                errorMessage = "Error al preparar la cámara: ${e.message}"
                                showErrorDialog = true
                            }
                        }
                        else -> {
                            permisoLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TreeColors.MediumGreen
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (imagenBitmap == null) "📸 Tomar foto" else "📸 Cambiar foto",
                    color = TreeColors.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            imagenBitmap?.let { bitmap ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Imagen del árbol",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { onImagenBitmapChange(null) }
                ) {
                    Text(
                        text = "🗑️ Eliminar foto",
                        color = TreeColors.MediumGreen
                    )
                }
            }
        }
    }

    // Dialog de error
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(
                    onClick = { showErrorDialog = false }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

// SOLUCIÓN 4: Saver mejorado para Bitmap con manejo de errores
val BitmapSaver: Saver<Bitmap?, Any> = Saver(
    save = { bitmap ->
        if (bitmap != null) {
            try {
                val stream = ByteArrayOutputStream()
                // Usar compresión JPEG con calidad 80 para reducir tamaño
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                val bytes = stream.toByteArray()
                stream.close()

                // Solo guardar si el tamaño no es excesivo (< 2MB)
                if (bytes.size < 2 * 1024 * 1024) {
                    Base64.encodeToString(bytes, Base64.DEFAULT)
                } else {
                    // Si es muy grande, escalar antes de guardar
                    val ratio = sqrt(2 * 1024 * 1024.0 / bytes.size)
                    val scaledBitmap = bitmap.scale(
                        (bitmap.width * ratio).toInt(),
                        (bitmap.height * ratio).toInt()
                    )
                    val scaledStream = ByteArrayOutputStream()
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, scaledStream)
                    val scaledBytes = scaledStream.toByteArray()
                    scaledStream.close()

                    Base64.encodeToString(scaledBytes, Base64.DEFAULT)
                }
            } catch (e: Exception) {
                // Si hay error al serializar, devolver null
                null
            }
        } else {
            null
        }
    },
    restore = { savedValue ->
        try {
            savedValue?.let { imageString ->
                val bytes = Base64.decode(imageString as String, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
        } catch (e: Exception) {
            // Si hay error al deserializar, devolver null
            null
        }
    }
)