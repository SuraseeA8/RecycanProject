package com.example.lab10mysql_registerlogin.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.R
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCustomerScreen(navController: NavHostController, viewModel: RecycanViewModel, role: String) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier.fillMaxSize()) {
                CustomerDrawerMenu(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    viewModel, "ผู้ซื้อ", navController
                )
            }
        }
    ) {
        Scaffold(
            containerColor = Color(0xFF7DBB7D),
            topBar = {
                CustomerTopBar {
                    scope.launch { drawerState.open() }
                }
            },
            bottomBar = { CustomerBottomNavBar(navController) }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                CustomerProfileScreen(viewModel, "ผู้ซื้อ")

                Spacer(modifier = Modifier.height(20.dp))

                // ===== ปุ่ม ซื้อขยะ =====
                Button(
                    onClick = {
                        navController.navigate(Screen.BuyerCategory.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(100.dp)
                        .padding(vertical = 10.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.width(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.buy),
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "ซื้อขยะ", fontSize = 20.sp)
                    }
                }

                // ===== ปุ่ม ประวัติการซื้อ =====
                Button(
                    onClick = {
                        navController.navigate(Screen.PurchaseHistory.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(100.dp)
                        .padding(vertical = 10.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.width(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.history),
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF7DBB7D))
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "menu",
            tint = Color.White,
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterStart)
                .clickable { onMenuClick() }
        )
        Image(
            painter = painterResource(id = R.drawable.recycan_logo),
            contentDescription = "logo",
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center)
        )
    }
}


@Composable
fun CustomerDrawerMenu(modifier: Modifier, viewModel: RecycanViewModel, role: String, navController: NavController) {

    val user = viewModel.currentUser.value
    val imageUrl = "http://192.168.1.10:3000/images/${user?.user_image}"

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(Color(0xFF8BC68B))
            .padding(15.dp)
    ) {
        Text(text = "บัญชีผู้ใช้", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            placeholder = painterResource(R.drawable.default_profile),
            error = painterResource(R.drawable.default_profile),
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = user?.user_name ?: "", color = Color.Black, fontSize = 20.sp)
        Text(text = "สถานะ : $role", color = Color.Black)

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 2f
                    drawLine(Color.Black, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth)
                    drawLine(Color.Black, Offset(0f, size.height), Offset(size.width, size.height), strokeWidth)
                },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Button(
                onClick = { navController.navigate(Screen.EditCustomerScreen.route) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("แก้ไขโปรไฟล์ผู้ใช้", color = Color.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("ออกจากระบบ")
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = viewModel.isSellerMode.value,
                modifier = Modifier.scale(1.4f).padding(10.dp),
                onCheckedChange = {
                    viewModel.isSellerMode.value = it
                    if (it) {
                        navController.navigate(Screen.HomeSellerScreen.route)
                    } else {
                        navController.navigate(Screen.HomeCustomerScreen.route)
                    }
                },
                thumbContent = {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .background(Color.White, shape = CircleShape)
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFFE53935),
                    checkedBorderColor = Color(0xFFE53935),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFF4CAF50),
                    uncheckedBorderColor = Color(0xFF4CAF50),
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
fun CustomerProfileScreen(viewModel: RecycanViewModel, role: String) {

    val user = viewModel.currentUser.value
    val imageUrl = "http://192.168.1.10:3000/images/${user?.user_image}"

    LaunchedEffect(Unit) {
        viewModel.loadUserById(1)
    }

    Column {
        user?.let { }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.3f)
            .border(width = 4.dp, color = Color(0xFF4CAF50)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.default_profile),
                error = painterResource(R.drawable.default_profile),
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = user?.user_name ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "สถานะ : $role",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }
    }
}


@Composable
fun CustomerBottomNavBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .fillMaxHeight(0.15f),
        containerColor = Color(0xFF4CAF50)
    ) {
        // ปุ่ม Home
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.HomeCustomerScreen.route) },
            icon = {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFF2E7D33), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.home),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        )

        // ===== ปุ่ม "check" → WasteList =====
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.WasteList.route) },
            icon = {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFF81C784), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.salelist),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        )

        // ปุ่ม Profile
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.EditCustomerScreen.route) },
            icon = {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFF81C784), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        )
    }
}