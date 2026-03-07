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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.data.model.HistoryListing
import com.example.lab10mysql_registerlogin.utils.SharedPreferencesManager
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel

@Composable
fun ListScreen(
    navController: NavController,
    vm: RecycanViewModel
) {
    val context = LocalContext.current
    val prefs = SharedPreferencesManager(context)
    val userId = prefs.getUserId()

    // 🔹 ดึงข้อมูลตาม UserId ของคนที่ล็อกอิน
    LaunchedEffect(Unit) {
        vm.fetchListings(userId)
    }

    val topBarGreen = Color(0xFF4CAF50)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(topBarGreen)
                    .padding(24.dp)
            ) {
                Text(
                    text = "รายการขายของคุณ",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // สรุปจำนวนรายการ
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ทั้งหมด ${vm.historyListings.size} รายการ",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // รายการ LazyColumn
            if (vm.historyListings.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("ไม่มีรายการขาย", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(vm.historyListings) { item ->
                        ListingItem(item = item, navController = navController)
                    }
                }
            }
        }

        // ปุ่มดูประวัติ (ถ้าต้องการแยกหน้า)
        Button(
            onClick = { /* ไปหน้าประวัติการขายที่ยืนยันแล้ว */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .fillMaxWidth(0.8f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("ดูประวัติการขาย", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ListingItem(item: HistoryListing, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.size(40.dp)
            ) {
                AsyncImage(
                    model = "http://10.0.2.2:3000/uploads/${item.image_url}",
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)) {
                Text(
                    text = item.category_name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = "น้ำหนัก: ${item.weight} กก.",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
                Text(
                    text = "${item.price} บาท",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "แก้ไข/ลบ",
                    color = Color.Blue,
                    fontSize = 13.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navController.navigate("EditDelete_screen/${item.listing_id}")
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
                
                Surface(
                    color = when(item.listing_state) {
                        "สำเร็จ" -> Color(0xFFE8F5E9)
                        else -> Color(0xFFFFF3E0)
                    },
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = item.listing_state ?: "รอการซื้อ",
                        color = when(item.listing_state) {
                            "สำเร็จ" -> Color(0xFF4CAF50)
                            else -> Color(0xFFFF9800)
                        },
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
