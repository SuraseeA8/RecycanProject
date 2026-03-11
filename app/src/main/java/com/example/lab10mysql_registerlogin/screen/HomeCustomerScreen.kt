package com.example.lab10mysql_registerlogin.screen

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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lab10mysql_registerlogin.R
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.utils.SharedPreferencesManager
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCustomerScreen(navController: NavHostController, viewModel: RecycanViewModel, role: String) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.isSellerMode.value = false
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            CustomerDrawerMenu(viewModel = viewModel, role = "ผู้ซื้อ", navController = navController)
        }
    ) {
        Scaffold(
            containerColor = Color(0xFF7DBB7D),
            topBar = { CustomerTopBar { scope.launch { drawerState.open() } } },
            bottomBar = { CustomerBottomNavBar(navController) }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 🔹 เพิ่มความสูง Spacer ตรงนี้ถ้าอยากให้เนื้อหาหน้าหลักเลื่อนลงมาอีก
                Spacer(modifier = Modifier.height(30.dp)) 
                
                CustomerProfileScreen(viewModel, "ผู้ซื้อ")
                
                Spacer(modifier = Modifier.height(20.dp))

                // ปุ่ม ซื้อขยะ
                Button(
                    onClick = { navController.navigate(Screen.BuyerCategory.route) },
                    modifier = Modifier.fillMaxWidth(0.9f).height(100.dp).padding(vertical = 10.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.width(40.dp), contentAlignment = Alignment.Center) {
                            Image(painter = painterResource(R.drawable.buy),
                                contentDescription = null, modifier = Modifier.size(35.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "ซื้อขยะ", fontSize = 20.sp)
                    }
                }

                // ปุ่ม ประวัติการซื้อ
                Button(
                    onClick = { navController.navigate(Screen.PurchaseHistory.route) },
                    modifier = Modifier.fillMaxWidth(0.9f).height(100.dp).padding(vertical = 10.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.width(40.dp), contentAlignment = Alignment.Center) {
                            Image(painter = painterResource(R.drawable.history),
                                contentDescription = null, modifier = Modifier.size(35.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "ประวัติการซื้อ", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomerTopBar(onMenuClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF7DBB7D)).statusBarsPadding().padding(horizontal = 16.dp, vertical = 4.dp)) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = "menu", tint = Color.White,
            modifier = Modifier.size(50.dp).align(Alignment.CenterStart).clickable { onMenuClick() })
        Image(painter = painterResource(id = R.drawable.recycan_logo), contentDescription = "logo",
            modifier = Modifier.size(120.dp).align(Alignment.Center))
    }
}

@Composable
fun CustomerDrawerMenu(viewModel: RecycanViewModel, role: String, navController: NavController) {
    val context = LocalContext.current
    val user = viewModel.currentUser.value

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(Color(0xFF8BC68B))
            .statusBarsPadding() // 🔹 เพิ่มตรงนี้เพื่อให้ข้อความเลื่อนลงมาไม่ติดขอบบน
            .padding(15.dp)
    ) {
        Text(text = "บัญชีผู้ใช้", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(R.drawable.default_profile),
            contentDescription = null,
            modifier = Modifier.size(80.dp).clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = user?.user_name ?: "กำลังโหลด...", color = Color.Black, fontSize = 20.sp)
        Text(text = "สถานะ : $role", color = Color.Black)
        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { navController.navigate(Screen.EditCustomerScreen.route) },
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
                SharedPreferencesManager(context).logout()
                navController.navigate(Screen.Login.route) { 
                    popUpTo(0) { inclusive = true } 
                }
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
                Text(text = "เปลี่ยนประเภทผู้ใช้", color = Color.Black, fontSize = 18.sp)
                Text(text = "สถานะ : $role", color = Color.Black, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun CustomerProfileScreen(viewModel: RecycanViewModel, role: String) {
    val context = LocalContext.current
    val userId = SharedPreferencesManager(context).getUserId()
    val user = viewModel.currentUser.value

    LaunchedEffect(Unit) {
        if (userId != 0) {
            viewModel.loadUserById(userId)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(0.9f).height(140.dp)
            .border(width = 4.dp, color = Color(0xFF4CAF50), shape = RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.default_profile),
                contentDescription = null,
                modifier = Modifier.size(70.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(text = user?.user_name ?: "กำลังโหลด...", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "สถานะ : $role", color = Color.Gray, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun CustomerBottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .height(110.dp),
        containerColor = Color(0xFF4CAF50),
        tonalElevation = 0.dp
    ) {
        val items = listOf(
            Triple(Screen.HomeCustomerScreen.route, R.drawable.home, "Home"),
            Triple(Screen.WasteList.route, R.drawable.salelist, "Waste"),
            Triple(Screen.EditCustomerScreen.route, R.drawable.profile, "Profile")
        )

        items.forEach { (route, icon, label) ->
            val isSelected = currentRoute == route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = null,
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
                icon = {
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 65.dp else 55.dp)
                            .background(if (isSelected) Color(0xFF2E7D33) else Color(0xFF81C784), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(icon),
                            contentDescription = label,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        }
    }
}
