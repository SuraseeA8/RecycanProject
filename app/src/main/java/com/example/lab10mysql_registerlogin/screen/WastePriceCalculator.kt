package com.example.lab10mysql_registerlogin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lab10mysql_registerlogin.MyTopAppBar // import ให้ถูกต้องด้วยครับ
import com.example.lab10mysql_registerlogin.data.model.Category
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import coil.compose.AsyncImage


// โทนสีจากภาพต้นแบบ (ปรับแต่งได้ตามต้องการ)
val TopBarGreen = Color(0xFF8DC68F)
val ButtonGreen = Color(0xFF55B159)
val SheetGreen = Color(0xFFCFE2D2)

@Composable
fun WastePriceCalculator(
    navController: NavHostController,
    viewModel: RecycanViewModel
) {
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var weight by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    // States สำหรับการคำนวณและ Bottom Sheet
    var resultPrice by remember { mutableStateOf<Double?>(null) }
    var localError by remember { mutableStateOf<String?>(null) }
    var showResultSheet by remember { mutableStateOf(false) }

    val categoryList = viewModel.categoryList

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    Scaffold(
        topBar = {
            // เรียกใช้ MyTopAppBar ที่เราสร้างไว้ตรงนี้ได้เลย!
            MyTopAppBar(
                title = "คำนวณราคาตามน้ำหนัก",
                navController = navController
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // ===== Dropdown เลือกประเภทขยะ =====
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedCategory?.category_name ?: "เลือกประเภทขยะ",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    leadingIcon = {
                        selectedCategory?.let { category ->
                            AsyncImage(
                                model = "http://10.0.2.2:3000/uploads/${category.image_url}",
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.ArrowDropDown, null, tint = Color.Black)
                        }
                    }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categoryList.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.category_name) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ===== Weight Input (ช่องกรอกน้ำหนัก) =====
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                placeholder = { Text("กรอกน้ำหนัก") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                trailingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text("กิโลกรัม", color = Color.Black)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Black)
                    }
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // ===== Calculate Button =====
            Button(
                onClick = {
                    calculateWastePrice(
                        selectedCategory = selectedCategory,
                        weightText = weight,
                        onResult = { total ->
                            resultPrice = total
                            localError = null
                            showResultSheet = true
                        },
                        onError = { errorMsg ->
                            localError = errorMsg
                        }
                    )
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGreen
                )
            ) {
                Text("คำนวณราคา", color = Color.White, fontSize = 18.sp)
            }

            // แสดง Error (ถ้ามี)
            localError?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(it, color = Color.Red)
            }
        }
    }

    // เรียกใช้ฟังก์ชันที่ 2 (Bottom Sheet)
    if (showResultSheet && resultPrice != null) {
        ResultBottomSheet(
            selectedCategory = selectedCategory,
            resultPrice = resultPrice!!,
            onDismissRequest = { showResultSheet = false }
        )
    }
}

// ================= 2. ฟังก์ชัน Bottom Sheet =================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultBottomSheet(
    selectedCategory: Category?,
    resultPrice: Double,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        containerColor = SheetGreen,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 40.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("ผลการคำนวณ", fontSize = 22.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = "${selectedCategory?.price_per_kg?.toInt() ?: 0} บาท / กก.",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "${resultPrice.toInt()} บาท",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onDismissRequest,
                modifier = Modifier
                    .width(180.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGreen
                )
            ) {
                Text("ยกเลิก", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

// ================= 1. ฟังก์ชันคำนวณราคา =================
fun calculateWastePrice(
    selectedCategory: Category?,
    weightText: String,
    onResult: (Double) -> Unit,
    onError: (String) -> Unit
) {
    val weightValue = weightText.toDoubleOrNull()

    when {
        selectedCategory == null -> {
            onError("กรุณาเลือกประเภทขยะ")
        }
        weightValue == null -> {
            onError("กรุณากรอกน้ำหนักให้ถูกต้อง")
        }
        else -> {
            val total = selectedCategory.price_per_kg * weightValue
            onResult(total)
        }
    }
}