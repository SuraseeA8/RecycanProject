package com.example.recycanproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.R
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.utils.SharedPreferencesManager
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSellerScreen(navController: NavHostController, viewModel: RecycanViewModel, role: String) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier.fillMaxSize()) {
                SellerDrawerMenu(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    viewModel, role, navController
                )
            }
        }
    ) {
        Scaffold(
            containerColor = Color(0xFF7DBB7D),
            topBar = {
                SellerTopBar {
                    scope.launch { drawerState.open() }
                }
            },
            bottomBar = { SellerBottomNavBar(navController) }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                SellerProfileScreen(viewModel, role)

                Spacer(modifier = Modifier.height(20.dp))

                // ปุ่ม ขายขยะ
                Button(
                    onClick = { navController.navigate(Screen.SellWaste.route) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(100.dp)
                        .padding(vertical = 10.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(R.drawable.sale), contentDescription = null, modifier = Modifier.size(35.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "ขายขยะรีไซเคิล", fontSize = 20.sp)
                    }
                }

                // ปุ่ม คำนวณราคา
                Button(
                    onClick = { navController.navigate(Screen.WPC.route) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(100.dp)
                        .padding(vertical = 10.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(R.drawable.calculate), contentDescription = null, modifier = Modifier.size(30.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "คำนวณราคามาตรฐาน", fontSize = 20.sp)
                    }
                }

                // ปุ่ม ประวัติการขาย
                Button(
                    onClick = { navController.navigate(Screen.List.route) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(100.dp)
                        .padding(vertical = 10.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(R.drawable.history), contentDescription = null, modifier = Modifier.size(35.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "รายการขายของคุณ", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun SellerTopBar(onMenuClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF7DBB7D)).padding(16.dp)) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "menu",
            tint = Color.White,
            modifier = Modifier.size(50.dp).align(Alignment.CenterStart).clickable { onMenuClick() }
        )
        Image(
            painter = painterResource(id = R.drawable.recycan_logo),
            contentDescription = "logo",
            modifier = Modifier.size(120.dp).align(Alignment.Center)
        )
    }
}

@Composable
fun SellerDrawerMenu(modifier: Modifier, viewModel: RecycanViewModel, role: String, navController: NavController) {
    val user = viewModel.currentUser.value
    val imageUrl = "http://10.0.2.2:3000/images/${user?.user_image}"

    Column(modifier = Modifier.fillMaxHeight().width(280.dp).background(Color(0xFF8BC68B)).padding(15.dp)) {
        Text(text = "บัญชีผู้ใช้", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            placeholder = painterResource(R.drawable.default_profile),
            error = painterResource(R.drawable.default_profile),
            modifier = Modifier.size(80.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = user?.user_name ?: "กำลังโหลด...", color = Color.Black, fontSize = 20.sp)
        Text(text = "สถานะ : $role", color = Color.Black)
        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { navController.navigate(Screen.EditSellerScreen.route) },
            modifier = Modifier.fillMaxWidth().drawBehind {
                val strokeWidth = 2f
                drawLine(Color.Black, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth)
                drawLine(Color.Black, Offset(0f, size.height), Offset(size.width, size.height), strokeWidth)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, null, tint = Color.Black)
                Spacer(modifier = Modifier.width(10.dp))
                Text("แก้ไขโปรไฟล์ผู้ใช้", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { 
                SharedPreferencesManager(navController.context).logout()
                navController.navigate(Screen.Login.route) { popUpTo(0) }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
            shape = RoundedCornerShape(30.dp)
        ) { Text("ออกจากระบบ") }

        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = viewModel.isSellerMode.value,
                modifier = Modifier.scale(1.4f).padding(10.dp),
                onCheckedChange = {
                    viewModel.isSellerMode.value = it
                    if (it) navController.navigate(Screen.HomeSellerScreen.route)
                    else navController.navigate(Screen.HomeCustomerScreen.route)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFFE53935),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFF4CAF50)
                )
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(text = "เปลี่ยนประเภทผู้ใช้", color = Color.Black, fontSize = 20.sp)
                Text(text = "สถานะ : $role", color = Color.Black, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun SellerProfileScreen(viewModel: RecycanViewModel, role: String) {
    val context = LocalContext.current
    val prefs = SharedPreferencesManager(context)
    val userId = prefs.getUserId()

    val user = viewModel.currentUser.value
    val imageUrl = "http://10.0.2.2:3000/images/${user?.user_image}"

    LaunchedEffect(Unit) {
        if (userId != 0) {
            viewModel.loadUserById(userId)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(140.dp)
            .border(width = 4.dp, color = Color(0xFF4CAF50), shape = RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.default_profile),
                error = painterResource(R.drawable.default_profile),
                modifier = Modifier.size(70.dp).clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(text = user?.user_name ?: "กำลังโหลด...", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
                Text(text = "สถานะ : $role", color = Color.Gray, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun SellerBottomNavBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)).height(80.dp),
        containerColor = Color(0xFF4CAF50)
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate(Screen.HomeSellerScreen.route) },
            icon = { Icon(painterResource(R.drawable.home), null, modifier = Modifier.size(24.dp), tint = Color.White) },
            label = { Text("หน้าแรก", color = Color.White) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.History.route) },
            icon = { Icon(painterResource(R.drawable.salelist), null, modifier = Modifier.size(24.dp), tint = Color.White) },
            label = { Text("รายการขาย", color = Color.White) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.Profile.route) },
            icon = { Icon(painterResource(R.drawable.profile), null, modifier = Modifier.size(24.dp), tint = Color.White) },
            label = { Text("โปรไฟล์", color = Color.White) }
        )
    }
}
