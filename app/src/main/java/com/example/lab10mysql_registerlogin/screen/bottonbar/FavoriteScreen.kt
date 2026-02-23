package com.example.lab6

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel

@Composable
fun FavoriteScreen(navController: NavHostController, viewModel: RecycanViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Image(
//            painter = painterResource(),
//            contentDescription = "Friend2",
//            modifier = Modifier.size(400.dp)
//        )
        Text(
            text = "nut"
        )

    }
}