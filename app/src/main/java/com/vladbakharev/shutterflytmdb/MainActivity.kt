package com.vladbakharev.shutterflytmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vladbakharev.shutterflytmdb.navigation.NavigationStack
import com.vladbakharev.shutterflytmdb.ui.theme.ShutterflyTMDBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShutterflyTMDBTheme {
                NavigationStack()
            }
        }
    }
}