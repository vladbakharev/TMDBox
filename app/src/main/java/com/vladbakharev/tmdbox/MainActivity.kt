package com.vladbakharev.tmdbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vladbakharev.tmdbox.navigation.NavigationStack
import com.vladbakharev.tmdbox.ui.theme.TMDBoxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBoxTheme {
                NavigationStack()
            }
        }
    }
}