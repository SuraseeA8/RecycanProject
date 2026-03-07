package com.example.recycanproject

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.compose.material3.SwitchDefaults
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.R
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSellerScreen(navController: NavHostController, viewModel: RecycanViewModel,role: String) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                SellerDrawerMenu(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    viewModel,"ผู้ขาย",navController
                )

            }
        }

    ) {
        Scaffold(
            containerColor = Color(0xFF7DBB7D),
            topBar = {
                SellerTopBar {
                    scope.launch {
                        drawerState.open()
                    }
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

                SellerProfileScreen(viewModel, "ผู้ขาย")

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        navController.navigate(Screen.SellWaste.route)
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

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .width(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.sale),
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                        }



                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "ขายขยะรีไซเคิล",
                            fontSize = 20.sp
                        )
                    }
                }

                Button(
                    onClick = {
                        navController.navigate(Screen.WPC.route)
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.calculate),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "คำนวณราคมาตรฐาน",
                            fontSize = 20.sp
                        )
                    }
                }



                Button(
                    onClick = {
                        navController.navigate(Screen.History.route)
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

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.history),
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "ประวัติการขาย",
                            fontSize = 20.sp
                        )
                    }
                }


            }
        }
    }
}



@Composable
fun SellerTopBar(onMenuClick: () -> Unit) {

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
fun SellerDrawerMenu(modifier: Modifier,viewModel: RecycanViewModel,role: String,navController: NavController) {

    val user = viewModel.currentUser.value
    val imageUrl = "http://192.168.1.10:3000/images/${user?.user_image}"


    Column(
        modifier = Modifier
            .fillMaxHeight()

            .width(280.dp)
            .background(Color(0xFF8BC68B))
            .padding(15.dp)
    ) {

        Text(
            text = "บัญชีผู้ใช้",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

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

        Text(
            text = user?.user_name ?: "",
            color = Color.Black,
            fontSize = 20.sp
        )

        Text(
            text = "สถานะ : $role",
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {

                val strokeWidth = 2f

                // เส้นด้านบน
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidth
                )

                // เส้นด้านล่าง
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            },

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),



            elevation = ButtonDefaults.buttonElevation(0.dp)

        ) {
            Button(
                onClick = { navController.navigate(Screen.EditSellerScreen.route) },
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                        ), modifier = Modifier.fillMaxWidth(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.Black
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        "แก้ไขโปรไฟล์ผู้ใช้",
                        color = Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {} ,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE53935)
            ),
            shape = RoundedCornerShape(30.dp)

        ) {

            Text("ออกจากระบบ")
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Switch(
                checked = viewModel.isSellerMode.value,

                modifier = Modifier
                    .scale(1.4f)   // ทำให้สวิตช์ใหญ่ขึ้น
                    .padding(10.dp),


                onCheckedChange = {

                    viewModel.isSellerMode.value = it

                    if (it) {
                        navController.navigate(Screen.HomeSellerScreen.route)
                    } else {
                        navController.navigate(Screen.HomeCustomerScreen.route)
                    }
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

                Text(
                    text = "เปลี่ยนประเภทผู้ใช้",
                    color = Color.Black,
                    fontSize = 20.sp
                )

                Text(
                    text = "สถานะ : $role",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }





    }
}







@Composable
fun SellerProfileScreen(viewModel: RecycanViewModel, role: String ) {

    val user = viewModel.currentUser.value
    val imageUrl = "http://192.168.1.10:3000/images/${user?.user_image}"

    LaunchedEffect(Unit) {
        viewModel.loadUserById(1)
    }

    Column {

        user?.let {



        }

    }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.3f)

            .border(
                width = 4.dp,
                color = Color(0xFF4CAF50)
            ),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
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
fun SellerBottomNavBar(navController: NavController) {

    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .fillMaxHeight(0.15f),
        containerColor = Color(0xFF4CAF50)
    ) {

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("home") },

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

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("check") },

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

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.EditSellerScreen.route) },

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