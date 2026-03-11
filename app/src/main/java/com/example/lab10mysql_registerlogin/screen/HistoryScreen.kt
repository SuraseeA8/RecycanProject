package com.example.lab10mysql_registerlogin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.data.model.HistorySeller
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.utils.SharedPreferencesManager
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@Composable
fun HistoryScreen(
    navController: NavController,
    vm: RecycanViewModel
) {
    val context = LocalContext.current
    val userId = SharedPreferencesManager(context).getUserId()

    LaunchedEffect(Unit) {
        if (userId != 0) {
            vm.fetchHistory(userId)
        }
    }

    val topBarGreen = Color(0xFF81C784)

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF2F2F2))) {
        // ===== TOP BAR =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(topBarGreen)
                .statusBarsPadding()
                .padding(vertical = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "ย้อนกลับ",
                    tint = Color.White
                )
            }

            Text(
                text = "ประวัติการขายสำเร็จ",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ทั้งหมด ${vm.historySellerList.size} รายการ",
            modifier = Modifier.padding(start = 20.dp),
            fontSize = 16.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (vm.historySellerList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("ไม่มีข้อมูลประวัติการขาย", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(vm.historySellerList) { item ->
                    HistoryItem(item = item, navController = navController)
                }
            }
        }
    }
}

@Composable
fun HistoryItem(item: HistorySeller, navController: NavController) {
    val imageName = when (item.category_name) {
        "พลาสติก PET" -> "pet.jpg"
        "พลาสติก HDPE" -> "hdpe.jpg"
        "พลาสติก PP" -> "pp.jpg"
        "กระดาษลัง" -> "cardboard.jpg"
        "กระดาษขาวดำ" -> "paper.jpg"
        "อลูมิเนียม" -> "aluminum.jpg"
        "เหล็ก" -> "steel.jpg"
        "ทองแดง" -> "copper.jpg"
        "สแตนเลส" -> "stainless.jpg"
        "ขวดแก้ว" -> "glass.jpg"
        else -> "default.jpg"
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.HistoryDetail.createRoute(item.transaction_id))
            },
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(Color(0xFFAED0AE), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "http://10.0.2.2:3000/uploads/$imageName",
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.category_name ?: "ประเภททั่วไป",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color.Black
                )
                Text(
                    text = "น้ำหนัก ${item.weight} กิโลกรัม",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "ผู้ซื้อ: ${item.buyer_name ?: "ไม่ระบุ"}",
                    color = Color(0xFF2E7D32),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${item.transaction_total} บ.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "สำเร็จ",
                    color = Color(0xFF4CAF50),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
