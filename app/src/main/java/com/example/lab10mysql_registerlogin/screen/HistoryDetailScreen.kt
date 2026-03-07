package com.example.lab10mysql_registerlogin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel

@Composable
fun HistoryDetailScreen(
    navController: NavController,
    vm: RecycanViewModel,
    transactionId: Int
) {
    // ดึงข้อมูลเมื่อเข้าหน้าจอ
    LaunchedEffect(transactionId) {
        vm.fetchDetail(transactionId)
    }

    val detail = vm.currentDetail
    val topBarGreen = Color(0xFF81C784) // สีเขียวตามแบบเพื่อน

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)) // พื้นหลังเทาอ่อน
    ) {
        // ===== TOP BAR =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(topBarGreen)
                .statusBarsPadding()
                .padding(vertical = 18.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "ย้อนกลับ",
                    tint = Color.White
                )
            }

            Text(
                text = "รายละเอียด",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // ===== IMAGE BOX (ดึงรูปตามประเภทขยะเหมือนเพื่อน) =====
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
                .background(
                    Color(0xFFAED0AE),
                    RoundedCornerShape(22.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            detail?.let { data ->
                val imageName = when (data.category_name) {
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

                AsyncImage(
                    model = "http://10.0.2.2:3000/uploads/$imageName",
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // ===== CATEGORY CARD (ข้อมูลขยะ) =====
        Card(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = detail?.category_name ?: "-",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "น้ำหนัก ${detail?.weight ?: "-"} กิโลกรัม",
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== INFO CARD (ข้อมูลผู้ซื้อ) =====
        Card(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("ชื่อผู้ซื้อ", color = Color.Gray)
                Text(
                    text = detail?.buyer_name ?: "-",
                    fontSize = 17.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(12.dp))
                Text("เบอร์โทรศัพท์ผู้ซื้อ", color = Color.Gray)
                Text(
                    text = detail?.buyer_phone ?: "-",
                    fontSize = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // ===== TOTAL PRICE =====
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "ราคารวมที่ได้รับ", color = Color.Gray)
            Text(
                text = "${detail?.transaction_total ?: "-"} บาท",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // ===== BACK BUTTON =====
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(bottom = 30.dp)
                .height(56.dp)
        ) {
            Text(text = "กลับไปหน้าประวัติ", color = Color.White, fontSize = 18.sp)
        }
    }
}