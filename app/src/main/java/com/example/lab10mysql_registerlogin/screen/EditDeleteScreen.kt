package com.example.lab10mysql_registerlogin.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.MyTopAppBar
import com.example.lab10mysql_registerlogin.data.model.ListingRequest
import com.example.lab10mysql_registerlogin.utils.SharedPreferencesManager
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDeleteScreen(
    navController: NavController,
    viewModel: RecycanViewModel,
    listing_id: Int
) {

    val context = LocalContext.current
    val prefs = SharedPreferencesManager(context)
    val userId = prefs.getUserId()

    val item = viewModel.historyListings.find { it.listing_id == listing_id }

    val category = viewModel.categoryList.find {
        it.category_name == item?.category_name
    }

    val pricePerKg = item?.price_per_kg ?: category?.price_per_kg ?: 0.0

    var weight by remember { mutableStateOf(item?.weight ?: 1.0) }
    var address by remember { mutableStateOf(item?.place ?: "") }
    var phone by remember { mutableStateOf(item?.phone ?: "") }

    var sellTime by remember {
        mutableStateOf(item?.sell_time?.split(" ")?.get(0) ?: "")
    }

    val totalPrice = pricePerKg * weight

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "แก้ไข/ลบรายการ",
                navController = navController
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (item != null) {

                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            AsyncImage(
                                model = "http://10.0.2.2:3000/uploads/${item.image_url}",
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = item.category_name,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "$pricePerKg บาท / กก.",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Divider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            color = Color.Gray
                        )

                        Text(
                            text = "ระบุจำนวน",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .background(Color(0xFFF5F5F5), RoundedCornerShape(24.dp))
                                .border(1.5.dp, Color.Gray, RoundedCornerShape(24.dp)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            IconButton(
                                onClick = {
                                    if (weight > 1) weight -= 1.0
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("-", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }

                            Text(
                                text = String.format("%.1f", weight),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(2f),
                                textAlign = TextAlign.Center
                            )

                            IconButton(
                                onClick = { weight += 1.0 },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("+", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "กิโลกรัม",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("ระบุที่อยู่สำหรับรับของ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("เบอร์โทรศัพท์") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                DateContent(
                    selectedDate = sellTime,
                    onDateSelected = {
                        sellTime = it
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "ราคาที่ได้ : $totalPrice บาท",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(Color.Red),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("ลบรายการ", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { showConfirmDialog = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF55B159)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("ยืนยันการแก้ไข", color = Color.White)
                    }
                }
            }
        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("ยืนยันการแก้ไข") },
            text = { Text("ต้องการบันทึกการเปลี่ยนแปลงหรือไม่") },
            confirmButton = {

                TextButton(onClick = {

                    val request = ListingRequest(
                        weight = weight,
                        price = totalPrice,
                        place = address,
                        phone = phone,
                        sell_time = sellTime,
                        seller_id = userId,
                        category_id = category?.category_id ?: 0
                    )

                    viewModel.updateItem(listing_id, request) {
                        Toast.makeText(context, "แก้ไขสำเร็จ", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }

                    showConfirmDialog = false

                }) { Text("ตกลง") }

            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("ยกเลิก")
                }
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("ยืนยันการลบ") },
            text = { Text("ต้องการลบรายการนี้หรือไม่") },
            confirmButton = {

                TextButton(onClick = {

                    viewModel.deleteItem(listing_id) {
                        Toast.makeText(context, "ลบสำเร็จ", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }

                    showDeleteDialog = false

                }) { Text("ลบ", color = Color.Red) }

            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("ยกเลิก")
                }
            }
        )
    }
}