package com.example.lab10mysql_registerlogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.lab10mysql_registerlogin.navigation.NavGraph
import com.example.lab10mysql_registerlogin.ui.theme.Lab10MySQL_RegisterLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab10MySQL_RegisterLoginTheme {
                MyScreen()
            }
        }
    }
}

@Composable
fun MyScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        NavGraph(navController = navController)
    }
}