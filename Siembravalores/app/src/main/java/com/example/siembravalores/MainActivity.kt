package com.example.siembravalores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.siembravalores.ui.theme.SiembraValoresTheme
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        setContent {
            SiembraValoresTheme {
                SiembraValoresApp()
            }
        }
    }
}

