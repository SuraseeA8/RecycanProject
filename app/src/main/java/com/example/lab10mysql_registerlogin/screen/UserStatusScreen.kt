package com.example.recycan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.draw.shadow
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel

@Composable
fun UserStatusScreen(navController: NavController, viewModel: RecycanViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF81C784)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "สถานะของคุณ",
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        // ปุ่มผู้ขาย
        Button(
            //navController.navigate(Screen.หน้าแรกผู้ขาย.route)
            onClick = { navController.navigate(Screen.HomeSellerScreen.route) },
            modifier = Modifier
                .width(296.dp)
                .height(44.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(10.dp)
                ),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "ผู้ขาย",
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ปุ่มผู้ซื้อ
        Button(
            //navController.navigate(Screen.หน้าแรกผู้ซื้อ.route)
            onClick = { navController.navigate(Screen.HomeCustomerScreen.route) },
            modifier = Modifier
                .width(296.dp)
                .height(44.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(10.dp)
                ),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "ผู้ซื้อ",
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}