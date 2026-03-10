package com.example.lab10mysql_registerlogin.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lab10mysql_registerlogin.R
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.utils.SharedPreferencesManager
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: RecycanViewModel
) {
    fun openFacebook(context: Context) {
        val uri = Uri.parse("fb://page/yourpageid")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            val playStore = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.katana")
            )
            context.startActivity(playStore)
        }
    }

    fun openGoogle(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.google.android.googlequicksearchbox")
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            val playStore = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.googlequicksearchbox")
            )
            context.startActivity(playStore)
        }
    }

    val context = LocalContext.current
    val sharedPref = SharedPreferencesManager(context)

    val bgGreen = Color(0xFF81C784)
    val panelColor = Color(0x66ECECEC)
    val buttonGreen = Color(0xFF4CAF50)

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val loginResult = viewModel.loginResult

    LaunchedEffect(loginResult) {
        loginResult?.let {
            if (!it.error) {
                sharedPref.saveLoginStatus(
                    isLoggedIn = true,
                    userId = it.user_id ?: 0,
                    userName = it.user_name ?: ""
                )

                viewModel.resetLogin()

                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                navController.navigate(Screen.HomeCustomerScreen.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            } else {
                viewModel.resetLogin()
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGreen)
    ) {


        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.size(200.dp)) {
                Image(
                    painter = painterResource(R.drawable.recycan_logo),
                    contentDescription = "Recycan Logo",
                    modifier = Modifier.size(200.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // ส่วนล่าง
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    panelColor,
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                )
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("อีเมล") },
                leadingIcon = { Icon(Icons.Default.MailOutline, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("รหัสผ่าน") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Login Button
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "กรุณากรอกให้ครบ", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    viewModel.login(userEmail = email, userPassword = password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = buttonGreen),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "เข้าสู่ระบบ", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ลิงก์ไปหน้า Register
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("ยังไม่มีบัญชี? ")
                TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                    Text(
                        text = "ลงทะเบียน",
                        color = Color(0xFF2E7D32),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFF2E7D32))
                Text(
                    text = " หรือ ",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color(0xFF2E7D32)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFF2E7D32))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.google),
                    contentDescription = "Google",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { openGoogle(context) }
                )
                Image(
                    painter = painterResource(R.drawable.facebook),
                    contentDescription = "Facebook",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { openFacebook(context) }
                )
                Image(
                    painter = painterResource(R.drawable.apple),
                    contentDescription = "Apple",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}