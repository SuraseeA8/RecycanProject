package com.example.lab10mysql_registerlogin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.foundation.layout.statusBarsPadding
import com.example.lab10mysql_registerlogin.data.api.RecycanClient
import com.example.lab10mysql_registerlogin.data.model.BuyerStatusResponse
import com.example.lab10mysql_registerlogin.data.model.BuyerStatusUpdateRequest

@Composable
fun UpdateStatusScreen(
    listingId: Int,
    navController: NavController
) {
    var status by remember { mutableStateOf("waiting") }
    var sellerName by remember { mutableStateOf("ไม่ทราบชื่อ") }
    var sellerPhone by remember { mutableStateOf("ไม่ทราบเบอร์") }
    var pickupPlace by remember { mutableStateOf("ไม่ทราบสถานที่") }
    var transactionId by remember { mutableStateOf(0) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(listingId) {
        loading = true
        errorMessage = null
        RecycanClient.recycanAPI.getBuyerStatus(listingId)
            .enqueue(object : Callback<BuyerStatusResponse> {
                override fun onResponse(
                    call: Call<BuyerStatusResponse>,
                    response: Response<BuyerStatusResponse>
                ) {
                    loading = false
                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        if (data != null) {
                            status = data.transaction_state ?: "waiting"
                            sellerName = data.seller_name ?: "ไม่ทราบชื่อ"
                            sellerPhone = data.seller_phone ?: "ไม่ทราบเบอร์"
                            pickupPlace = data.pickup_place ?: "ไม่ทราบสถานที่"
                            transactionId = data.transaction_id
                        } else {
                            errorMessage = "ข้อมูลว่าง"
                        }
                    } else {
                        errorMessage = "ไม่สามารถโหลดข้อมูล: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<BuyerStatusResponse>, t: Throwable) {
                    loading = false
                    errorMessage = "เกิดข้อผิดพลาด: ${t.localizedMessage}"
                    t.printStackTrace()
                }
            })
    }

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("กำลังโหลดข้อมูล...")
        }
        return
    }

    if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(errorMessage ?: "เกิดข้อผิดพลาด")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF81C784))
                .statusBarsPadding()
                .padding(vertical = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "อัปเดตสถานะ",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Seller Info Card
        Card(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("ผู้ขาย", color = Color.Gray)
                Text(sellerName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(12.dp))
                Text("สถานที่รับ", color = Color.Gray)
                Text(pickupPlace, fontSize = 17.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text("เบอร์ติดต่อ", color = Color.Gray)
                Text(sellerPhone, fontSize = 17.sp)
            }
        }

        Spacer(modifier = Modifier.height(35.dp))

        // Status Display
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("สถานะ", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .background(
                        color = when (status) {
                            "waiting" -> Color(0xFFFFA000)
                            "picking_up" -> Color(0xFF42A5F5)
                            "success" -> Color(0xFF4CAF50)
                            else -> Color.Gray
                        },
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = when (status) {
                        "waiting" -> "รอเข้ารับ"
                        "picking_up" -> "กำลังเข้ารับ"
                        "success" -> "รับขยะสำเร็จ"
                        else -> "ไม่ทราบสถานะ"
                    },
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Update Button
        Button(
            onClick = {
                val nextState = when (status) {
                    "waiting" -> "picking_up"
                    "picking_up" -> "success"
                    else -> status
                }

                val request = BuyerStatusUpdateRequest(
                    transaction_id = transactionId,
                    transaction_state = nextState
                )

                RecycanClient.recycanAPI.updateBuyerStatus(request)
                    .enqueue(object : Callback<BuyerStatusResponse> {
                        override fun onResponse(
                            call: Call<BuyerStatusResponse>,
                            response: Response<BuyerStatusResponse>
                        ) {
                            if (response.isSuccessful) {
                                status = nextState
                            }
                        }

                        override fun onFailure(call: Call<BuyerStatusResponse>, t: Throwable) {
                            t.printStackTrace()
                            errorMessage = "ไม่สามารถอัปเดตสถานะได้"
                        }
                    })
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(56.dp)
        ) {
            Text("อัปเดตสถานะ", color = Color.White, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(56.dp)
        ) {
            Text("กลับไปหน้ารายการขยะ", color = Color.White, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}