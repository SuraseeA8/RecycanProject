package com.example.lab10mysql_registerlogin.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.MyTopAppBar
import com.example.lab10mysql_registerlogin.R
import com.example.lab10mysql_registerlogin.data.model.ListingRequest
import com.example.lab10mysql_registerlogin.utils.SharedPreferencesManager
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
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

    var phone by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val monthStr = if (month < 10) "0$month" else "$month"
    val dayStr = if (day < 10) "0$day" else "$day"

    var sellTime by remember { mutableStateOf("$year-$monthStr-$dayStr") }

    val totalPrice = (category?.price_per_kg ?: 0.0) * weight

    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "รายละเอียดการขาย",
                navController = navController
            )
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (category != null) {
                val horizontalPadding = 20.dp
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row() {
                            AsyncImage(
                                model = "http://10.0.2.2:3000/uploads/${category.image_url}",
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = category.category_name,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                        }
                        Text(
                            text = "${category.price_per_kg} บาท / กก.",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Divider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            thickness = 1.dp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(8.dp))
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
                                onClick = { if (weight > 1) weight -= 1.0 },
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
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("ระบุที่อยู่สำหรับรับของ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding)
                        .defaultMinSize(minHeight = 140.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("เบอร์โทรศัพท์") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // สำหรับ DateContent ถ้าอยากให้กว้างเท่ากันด้วย
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)) {
                    DateContent(
                        selectedDate = sellTime,
                        onDateSelected = { date ->
                            sellTime = date
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "ราคาที่ได้ : $totalPrice บาท",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32) // สีเขียวเข้ม
                )

                Spacer(modifier = Modifier.weight(1f))


                Button(
                    onClick = {
                        if (address.isBlank() || phone.isBlank()) {
                            Toast.makeText(context, "กรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        if (sellTime.isBlank()) {
                            Toast.makeText(context, "กรุณาเลือกวันนัดหมาย", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val sellerId = prefs.getUserId()
                        val request = ListingRequest(
                            weight = weight,
                            price = totalPrice,
                            place = address,
                            phone = phone,
                            sell_time = "$sellTime 00:00:00",
                            seller_id = sellerId,
                            category_id = categoryId
                        )

                        viewModel.createListing(request) { success ->
                            if (success) {
                                showSuccessDialog = true
                            } else {
                                Toast.makeText(context, "บันทึกไม่สำเร็จ ❌", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF55B159))
                ) {
                    Text("ยืนยันการขาย", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                if (showSuccessDialog) {
                    Dialog(
                        onDismissRequest = {
                            showSuccessDialog = false
                            navController.popBackStack()
                        }
                    ) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .size(width = 280.dp, height = 220.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.check),
                                    contentDescription = null,
                                    modifier = Modifier.size(80.dp)
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Text(
                                    text = "ลงขายสำเร็จ",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateContent(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, y, m, d ->

            val monthFixed = m + 1
            val monthStr = if (monthFixed < 10) "0$monthFixed" else "$monthFixed"
            val dayStr = if (d < 10) "0$d" else "$d"

            val formattedDate = "$y-$monthStr-$dayStr"
            onDateSelected(formattedDate)

        },
        year,
        month,
        day
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("วันที่")

        Spacer(modifier = Modifier.width(16.dp))

        FilledIconButton(
            onClick = { datePickerDialog.show() }
        ) {
            Icon(
                imageVector = Icons.Outlined.DateRange,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(selectedDate)
    }
}

