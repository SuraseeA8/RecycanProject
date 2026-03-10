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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.lab10mysql_registerlogin.data.api.RecycanClient
import com.example.lab10mysql_registerlogin.data.model.BuyerDetail
import com.example.lab10mysql_registerlogin.data.model.BuyerDetailResponse
import com.example.lab10mysql_registerlogin.navigation.Screen

@Composable
fun PurchaseDetailScreen(
    id: String,
    navController: NavController
) {
    var detail by remember { mutableStateOf<BuyerDetail?>(null) }

    LaunchedEffect(Unit) {
        RecycanClient.recycanAPI.getBuyerHistoryDetail(id.toInt())
            .enqueue(object : Callback<BuyerDetailResponse> {
                override fun onResponse(
                    call: Call<BuyerDetailResponse>,
                    response: Response<BuyerDetailResponse>
                ) {
                    detail = response.body()?.data
                }

                override fun onFailure(call: Call<BuyerDetailResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
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

        // IMAGE
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color(0xFFAED0AE), RoundedCornerShape(22.dp)),
            contentAlignment = Alignment.Center
        ) {
            detail?.let {
                val imageName = when (it.category_name) {
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
                AsyncImage(
                    model = "http://10.0.2.2:3000/uploads/$imageName",
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // CATEGORY CARD
        Card(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(4.dp)
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

        // INFO CARD
        Card(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("ผู้ขาย", color = Color.Gray)
                Text(text = detail?.seller ?: "-", fontSize = 17.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(12.dp))
                Text("สถานที่รับ", color = Color.Gray)
                Text(text = detail?.place ?: "-", fontSize = 17.sp)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // PRICE
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "ราคารวม", color = Color.Gray)
            Text(
                text = "${detail?.transaction_total ?: "-"} บาท",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        // BACK BUTTON
        Button(
            onClick = { navController.navigate(Screen.PurchaseHistory.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(56.dp)
        ) {
            Text(text = "กลับไปหน้าประวัติการซื้อ", color = Color.White, fontSize = 20.sp)
        }
    }
}