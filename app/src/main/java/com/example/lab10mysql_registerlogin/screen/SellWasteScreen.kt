package com.example.lab10mysql_registerlogin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lab10mysql_registerlogin.MyTopAppBar // อย่าลืม Import TopAppBar ของเรา
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.utils.SharedPreferencesManager
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import coil.compose.AsyncImage

@Composable
fun SellWasteScreen(
    navController: NavController,
    viewModel: RecycanViewModel = viewModel()
) {

    val categories = viewModel.categoryList
    val context = LocalContext.current
    val prefs = SharedPreferencesManager(context)

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    // 1. นำ Scaffold มาครอบไว้ และเรียกใช้ MyTopAppBar
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "เลือกประเภทขยะ",
                navController = navController
            )
        }
    ) { paddingValues ->
        // 2. นำ paddingValues มาใส่ที่ Column เพื่อดันเนื้อหาลงมาให้อยู่ใต้ TopBar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            // 🔹 List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(categories) { category ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.SellWasteDetail.createRoute(category.category_id)
                                )
                            },
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFAED581)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            //horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically // จัดให้ข้อความและรูปอยู่กึ่งกลางแนวตั้ง
                        ) {
                            AsyncImage(
                                model = "http://10.0.2.2:3000/uploads/${category.image_url}",
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = category.category_name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)

                            )
                            Text(
                                text = "${category.price_per_kg} บาท / กก.",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

        }
    }
}