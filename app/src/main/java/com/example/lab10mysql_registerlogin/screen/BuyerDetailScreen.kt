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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.viewmodel.DetailVM
import com.example.lab10mysql_registerlogin.data.model.BuyerUiState
import com.example.lab10mysql_registerlogin.screen.TitleGreen

private val TopGreen = Color(0xFF81C784)
private val IconCircle = Color(0xFFB6D9B8)
private val BorderGray = Color(0xFFBDBDBD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerDetailScreen(
    listing_id: Int,
    onBack: () -> Unit,
    onBuySuccess: (transactionId: Int) -> Unit = {},
    vm: DetailVM = viewModel()
) {
    LaunchedEffect(listing_id) { vm.load(listing_id) }

    val detailState by vm.detail.collectAsState()
    val buyState by vm.buy.collectAsState()

    LaunchedEffect(buyState) {
        val s = buyState
        if (s is BuyerUiState.Success) {
            val txId = s.data.transaction_id
            if (txId != null) {
                onBuySuccess(txId)
                vm.clearBuyState()
            }
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(TopGreen)
            ) {
                CenterAlignedTopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .offset { IntOffset(0, 8.dp.roundToPx()) },
                    navigationIcon = {
                        IconButton(
                            onClick = { onBack() },
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    },
                    title = {
                        Text(
                            "ซื้อขยะ",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
                Divider(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    color = Color.White,
                    thickness = 1.dp
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "รายละเอียด",
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = TitleGreen,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal
            )

            Spacer(Modifier.height(12.dp))

            when (val dState = detailState) {
                is BuyerUiState.Loading -> {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = TopGreen)
                    }
                }
                is BuyerUiState.Error -> {
                    Text(dState.message, color = MaterialTheme.colorScheme.error)
                }
                is BuyerUiState.Success -> {
                    val d = dState.data

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 110.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFECECEC)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp, horizontal = 18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(86.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(IconCircle),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = toFullImageUrl(d.image_url),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize().padding(12.dp)
                                )
                            }

                            Spacer(Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "ประเภท: ${d.category_name}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = "น้ำหนัก: ${d.weight} กิโลกรัม",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    text = "ราคา: ${d.price} บาท",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(18.dp))

                    Text("สถานที่รับ/ส่ง", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))
                    Text(d.place, style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.height(18.dp))
                    Text("เบอร์โทรศัพท์", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))
                    Text(d.user_phone ?: "-", style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.height(18.dp))
                    Text("วันที่ลงขาย", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))
                    Text(d.createdAt ?: "-", style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.height(18.dp))
                    Text("ขายโดย", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))
                    Text(d.user_name, style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.weight(1f))

                    Button(
                        onClick = { vm.buyNow(d.listing_id, d.price) },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TopGreen),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text("ยืนยันการซื้อ", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(10.dp))

                    when (val b = buyState) {
                        is BuyerUiState.Loading -> Text(
                            "กำลังทำรายการ...",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        is BuyerUiState.Error -> Text(
                            b.message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        else -> {}
                    }
                }
            }
        }
    }
}

private fun toFullImageUrl(imageUrl: String?): String? {
    if (imageUrl.isNullOrBlank()) return null
    if (imageUrl.startsWith("http")) return imageUrl
    val base = "http://10.0.2.2:3000"
    return when {
        imageUrl.startsWith("/uploads/") -> base + imageUrl
        imageUrl.startsWith("uploads/") -> "$base/$imageUrl"
        else -> "$base/uploads/$imageUrl"
    }
}