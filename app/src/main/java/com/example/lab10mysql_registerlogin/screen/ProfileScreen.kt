package com.example.lab10mysql_registerlogin.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.utils.SharedPreferencesManager

@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPref = SharedPreferencesManager(context)

    val userId = sharedPref.getUserId()
    val userName = sharedPref.getUserName()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (userId == 0) {
            navController.navigate(Screen.Login.route) {
                popUpTo(0)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Profile", fontSize = 30.sp, fontWeight = FontWeight.Bold)

        Column(modifier = Modifier.fillMaxWidth()) {
            ProfileTextItem(text = "User ID: $userId")
            ProfileTextItem(text = "Name: $userName")
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navController.navigate(Screen.History.route) }, // แก้เป็นอันนี้
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
        ) {
            Text("ดูประวัติการขาย", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Button(
            onClick = { navController.navigate(Screen.History.route) },
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(bottom = 8.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text("ListSold", color = Color.White, fontSize = 18.sp)
        }
        Button(
            onClick = { navController.navigate(Screen.List.route) },
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(bottom = 8.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text("List", color = Color.White, fontSize = 18.sp)
        }

        Button(
            onClick = { navController.navigate(Screen.WPC.route) },
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(bottom = 8.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text("Waste Price Calculator", color = Color.White, fontSize = 18.sp)
        }
        Button(
            onClick = { navController.navigate(Screen.SellWaste.route) },
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(bottom = 8.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text("Sell Waste Detail", color = Color.White, fontSize = 18.sp)
        }

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Logout", color = Color.White, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }



    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Logout") },
            text = { Text("Do you want to sign out?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        sharedPref.logout()

                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Profile.route) { inclusive = true }
                        }

                        showDialog = false
                    }
                ) {
                    Text("Logout")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProfileTextItem(text: String) {
    Text(
        text = text,
        fontSize = 22.sp,
        modifier = Modifier.padding(vertical = 12.dp)
    )
}