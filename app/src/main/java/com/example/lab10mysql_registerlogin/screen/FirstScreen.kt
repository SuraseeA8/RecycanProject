package com.example.lab10mysql_registerlogin.screen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.lab10mysql_registerlogin.R
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel

// หน้าแรกสุด
@Composable
fun FirstScreen(navController: NavController,
                viewModel: RecycanViewModel) {

    val bgGreen = Color(0xFF81C784)
    val bottomPanel = Color(0x66ECECEC)
    val borderGreen = Color(0x6B2E7D32)
    val loginGreen = Color(0xFF4CAF50)
    val registerTextGreen = Color(0xFF295C1B)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGreen)
    ) {

        // โลโก้
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(250.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.recycan_logo),
                    contentDescription = "Recycan Logo",
                    modifier = Modifier.size(250.dp)
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // ส่วนล่าง
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    bottomPanel,
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                )
                .padding(horizontal = 24.dp, vertical = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ปุ่มเข้าสู่ระบบ
            Button(
                onClick = {
                    navController.navigate(Screen.Login.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = loginGreen
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = borderGreen
                )
            ) {
                Text(
                    text = "เข้าสู่ระบบ",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ปุ่มลงทะเบียบ
            Button(
                onClick = {
                    navController.navigate(Screen.Register.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = borderGreen
                )
            ) {
                Text(
                    text = "ลงทะเบียน",
                    color = registerTextGreen,
                    fontSize = 18.sp
                )
            }

        }
    }
}