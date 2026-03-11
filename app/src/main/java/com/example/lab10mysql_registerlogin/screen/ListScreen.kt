package com.example.lab10mysql_registerlogin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

    LaunchedEffect(Unit) {
        vm.fetchListings(userId,"รอการซื้อ")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF81C784))
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
                text = "รายการขายของคุณ",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== TOTAL TEXT =====
        Text(
            text = "ทั้งหมด ${vm.historyListings.size} รายการ",
            modifier = Modifier.padding(start = 20.dp),
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ===== รายการ LazyColumn =====
        if (vm.historyListings.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("ไม่มีรายการขาย", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(vm.historyListings) { item ->
                    ListingItem(item = item, navController = navController)
                }

                // สเปซท้ายลิสต์เพื่อให้ไม่โดนปุ่มบัง
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }

    // ===== Floating Bottom Button (ปุ่มทรงแคปซูล) =====
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { /* ไปหน้าประวัติการขาย */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)),
            shape = RoundedCornerShape(50) // ทรงแคปซูลตามแบบ
        ) {
            Text("ดูประวัติการขายที่สำเร็จ", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ListingItem(item: HistoryListing, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("EditDelete_screen/${item.listing_id}")
            },
        shape = RoundedCornerShape(18.dp), // มน 18 ตามแบบ
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // IMAGE BOX (กรอบสี่เหลี่ยมสีเขียวอ่อน)
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(
                        Color(0xFFAED0AE), // สีพื้นหลังรูปภาพตามแบบ
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "http://10.0.2.2:3000/uploads/${item.image_url}",
                    contentDescription = null,
                    modifier = Modifier.size(45.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.category_name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "น้ำหนัก ${item.weight} กิโลกรัม",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "ราคา ${item.price} บาท",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            // สถานะหรือปุ่มรายละเอียด
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "แก้ไข/ลบรายการ",
                    color = Color(0xFF2962FF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Badge แสดงสถานะ (รอ/สำเร็จ)
                Surface(
                    color = when(item.listing_state) {
                        "สำเร็จ" -> Color(0xFFE8F5E9)
                        else -> Color(0xFFFFF3E0)
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = item.listing_state ?: "รอการซื้อ",
                        color = when(item.listing_state) {
                            "สำเร็จ" -> Color(0xFF4CAF50)
                            else -> Color(0xFFFF9800)
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}