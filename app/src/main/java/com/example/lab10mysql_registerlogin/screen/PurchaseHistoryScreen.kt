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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.data.api.RecycanClient
import com.example.lab10mysql_registerlogin.data.model.HistorySeller
import com.example.lab10mysql_registerlogin.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun PurchaseHistoryScreen(navController: NavController) {

    var list by remember { mutableStateOf<List<HistorySeller>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                list = RecycanClient.recycanAPI.getHistory(1)
                loading = false
            } catch (e: Exception) {
                loading = false
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        // TOP BAR
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
                text = "ประวัติการซื้อ",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ทั้งหมด ${list.size} รายการ",
            modifier = Modifier.padding(start = 20.dp),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        when {
            loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("กำลังโหลดข้อมูล...")
                }
            }
            list.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("ไม่มีข้อมูลประวัติการซื้อ")
                }
            }
            else -> {
                LazyColumn {
                    items(list) { item ->
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
                            else -> ""
                        }

                        Card(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        Screen.PurchaseDetail.createRoute(item.transaction_id)
                                    )
                                },
                            shape = RoundedCornerShape(18.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
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
                                        modifier = Modifier.size(45.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.category_name ?: "-",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "น้ำหนัก ${item.weight} กิโลกรัม",
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "รวมสุทธิ ${item.transaction_total} บาท",
                                        fontSize = 14.sp
                                    )
                                }

                                Text(
                                    text = "รายละเอียด",
                                    color = Color(0xFF2962FF),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            onClick = { navController.navigate(Screen.HomeCustomerScreen.route) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp)
                                .height(56.dp)
                        ) {
                            Text(text = "กลับไปหน้าแรก", color = Color.White, fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }
    }
}