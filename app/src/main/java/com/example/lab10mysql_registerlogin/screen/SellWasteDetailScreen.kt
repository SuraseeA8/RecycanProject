package com.example.lab10mysql_registerlogin.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.lab10mysql_registerlogin.MyTopAppBar
import com.example.lab10mysql_registerlogin.data.model.ListingRequest
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.utils.SharedPreferencesManager
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel

@Composable
fun SellWasteDetailScreen(
    navController: NavController,
    viewModel: RecycanViewModel,
    categoryId: Int
) {
    val context = LocalContext.current
    val prefs = SharedPreferencesManager(context)
    val category = viewModel.categoryList.find { it.category_id == categoryId }

    var weight by remember { mutableStateOf(1.0) }
    var address by remember { mutableStateOf("") }

    val totalPrice = (category?.price_per_kg ?: 0.0) * weight

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                // เปลี่ยนชื่อหน้าให้เข้ากับบริบทว่ากำลังดูรายละเอียด
                title = "รายละเอียดการขาย",
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp), // 🟢 เพิ่ม Padding ซ้าย-ขวา ไม่ให้เนื้อหาติดขอบจอ
            horizontalAlignment = Alignment.CenterHorizontally // 🟢 บังคับให้ทุกอย่างในหน้านี้อยู่ตรงกลาง
        ) {

            if (category != null) {
                // 🔹 ส่วนข้อมูลขยะและปรับน้ำหนัก
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White), // ให้การ์ดสีขาวตัดกับพื้นหลัง
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = "http://10.0.2.2:3000/uploads/${category.image_url}",
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                        )
                        Text(
                            text = category.category_name,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${category.price_per_kg} บาท / กก.",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Text("ระบุจำนวน (กิโลกรัม)", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(12.dp))

                        // 🔹 ปุ่มปรับน้ำหนัก
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            FilledTonalButton(
                                onClick = { if (weight > 1) weight-- },
                                modifier = Modifier.size(48.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) { Text("-", fontSize = 24.sp) }

                            Text(
                                text = String.format("%.1f", weight), // แสดงทศนิยม 1 ตำแหน่งให้ดูสวย
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 24.dp)
                            )

                            FilledTonalButton(
                                onClick = { weight++ },
                                modifier = Modifier.size(48.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) { Text("+", fontSize = 20.sp) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 🔹 ช่องกรอกที่อยู่
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("ระบุที่อยู่สำหรับรับของ") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // 🔹 แสดงราคารวม
                Text(
                    text = "ราคาที่ได้ : $totalPrice บาท",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32) // สีเขียวเข้ม
                )

                // 🟢 ใช้ Spacer แบบ weight เพื่อดันปุ่มยืนยันไปอยู่ล่างสุดของหน้าจอ
                Spacer(modifier = Modifier.weight(1f))

                // 🔹 ปุ่มยืนยัน
                Button(
                    onClick = {
                        val sellerId = prefs.getUserId()
                        val request = ListingRequest(
                            weight = weight,
                            price = totalPrice,
                            place = address,
                            seller_id = sellerId,
                            category_id = categoryId
                        )

                        viewModel.createListing(request) { success ->
                            if (success) {
                                Toast.makeText(context, "บันทึกสำเร็จ ✅", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(context, "บันทึกไม่สำเร็จ ❌", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp), // ปรับปุ่มให้สูงและกดง่าย
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF55B159))
                ) {
                    Text("ยืนยันการขาย", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                // ❌ ลบปุ่ม Back สีแดงทิ้งไปแล้วนะครับ เพราะใช้ปุ่มบน TopBar แทนได้เลย
            }
        }
    }
}