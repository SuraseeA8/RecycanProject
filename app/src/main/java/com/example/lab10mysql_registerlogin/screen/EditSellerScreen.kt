package com.example.recycanproject

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.lab10mysql_registerlogin.R
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel

@Composable
fun EditSellerScreen(navController: NavHostController, viewModel: RecycanViewModel) {

    val user = viewModel.currentUser.value

    // ✅ FIX: ใช้ remember(user) ทุกตัว ให้ update เมื่อ user โหลดเสร็จ
    var name by remember(user) { mutableStateOf(user?.user_name ?: "") }
    var address by remember(user) { mutableStateOf(user?.user_address ?: "") }
    var phone by remember(user) { mutableStateOf(user?.user_phone ?: "") }
    val email = user?.user_email ?: ""

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        SellerEditProfileTopBar(navController)

        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            SellerEditProfileSection(
                name = name,
                address = address,
                phone = phone,
                onNameChange = { name = it },
                onAddressChange = { address = it },
                onPhoneChange = { phone = it },
                viewModel = viewModel
            )

            Spacer(modifier = Modifier.height(60.dp))

            SellerConfirmButton(
                name = name,
                address = address,
                phone = phone,
                email = email,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@Composable
fun SellerEditProfileTopBar(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f)
            .background(Color(0xFF7DBB7D)).padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.back),
            contentDescription = "Back",
            modifier = Modifier.size(25.dp).align(Alignment.CenterStart)
                .clickable { navController.navigate(Screen.HomeSellerScreen.route) }
        )
        Text(text = "แก้ไขโปรไฟล์", color = Color.White, fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun SellerEditProfileSection(
    name: String,
    address: String,
    phone: String,
    onNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    viewModel: RecycanViewModel,
) {
    val user = viewModel.currentUser.value

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        // ✅ FIX: ลบ imageUrl ออก ไม่มี user_image ใน DB แล้ว
        Image(
            painter = painterResource(R.drawable.default_profile),
            contentDescription = null,
            modifier = Modifier.size(160.dp).clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ชื่อ
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = name, onValueChange = onNameChange,
                label = { Text("ชื่อ - นามสกุล") },
                modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(15.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(modifier = Modifier.size(20.dp), imageVector = Icons.Default.Edit,
                contentDescription = null, tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ที่อยู่
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = address, onValueChange = onAddressChange,
                label = { Text("ที่อยู่") },
                modifier = Modifier.fillMaxWidth(0.9f).height(100.dp),
                shape = RoundedCornerShape(15.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(modifier = Modifier.size(20.dp), imageVector = Icons.Default.Edit,
                contentDescription = null, tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // โทรศัพท์
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = phone, onValueChange = onPhoneChange,
                label = { Text("เบอร์โทรศัพท์") },
                modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(15.dp),
                leadingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 8.dp)) {
                        Image(painter = painterResource(R.drawable.th_flag),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp).padding(2.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("+66")
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(modifier = Modifier.size(20.dp), imageVector = Icons.Default.Edit,
                contentDescription = null, tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Email (แก้ไม่ได้)
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = user?.user_email ?: "", onValueChange = {}, enabled = false,
                label = { Text("อีเมล") },
                modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(15.dp)
            )
        }
    }
}

@Composable
fun SellerConfirmButton(
    name: String, email: String, address: String, phone: String,
    viewModel: RecycanViewModel, navController: NavController
) {
    val context = LocalContext.current
    Button(
        onClick = {
            viewModel.updateSellerProfile(name, email, phone, address)
            Toast.makeText(context, "แก้ไขโปรไฟล์สำเร็จ", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.HomeSellerScreen.route) {
                popUpTo(Screen.HomeSellerScreen.route) { inclusive = true }
            }
        },
        modifier = Modifier.fillMaxWidth(0.6f).height(50.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
    ) {
        Text(text = "ยืนยัน", color = Color.White, fontSize = 18.sp)
    }
}