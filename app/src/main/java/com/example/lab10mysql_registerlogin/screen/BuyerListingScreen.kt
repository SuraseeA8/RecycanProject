package com.example.lab10mysql_registerlogin.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.R
import com.example.lab10mysql_registerlogin.viewmodel.ListingVM
import com.example.lab10mysql_registerlogin.data.model.BuyerUiState

private val TopGreen = Color(0xFF81C784)
private val LinkBlue = Color(0xFF0004FD)
private val IconCircle = Color(0xFFB6D9B8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerListingScreen(
    category_id: Int,
    onClickDetail: (Int) -> Unit,
    onBack: () -> Unit,
    vm: ListingVM = viewModel()
) {
    LaunchedEffect(category_id) { vm.load(category_id) }
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TopGreen)
                    .statusBarsPadding()
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { onBack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "ซื้อขยะ",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Spacer(Modifier.height(6.dp))

            when (val s = state) {
                is BuyerUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                        CircularProgressIndicator(color = TopGreen)
                    }
                }
                is BuyerUiState.Error -> {
                    Text(s.message, color = MaterialTheme.colorScheme.error)
                }
                is BuyerUiState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(s.data) { item ->
                            ListingCardMiddleStyle(
                                sellerName = item.user_name,
                                categoryName = item.category_name,
                                weightKg = item.weight,
                                price = item.price,
                                imageUrl = item.image_url,
                                onClickDetail = { onClickDetail(item.listing_id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ListingCardMiddleStyle(
    sellerName: String,
    categoryName: String,
    weightKg: Double,
    price: Double,
    imageUrl: String?,
    onClickDetail: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECECEC)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, Color(0xFFD0D0D0))
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "รายละเอียด",
                    color = LinkBlue,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onClickDetail() }
                )
            }

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(94.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(IconCircle),
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUrl.isNullOrBlank()) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize().padding(10.dp)
                        )
                    } else {
                        AsyncImage(
                            model = toFullImageUrl(imageUrl),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize().padding(10.dp)
                        )
                    }
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "ร้าน: $sellerName",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(text = "ประเภท: $categoryName", style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(2.dp))
                    Text(text = "น้ำหนัก: $weightKg กิโลกรัม", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Row(
                Modifier.fillMaxWidth().padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "ราคา: $price บาท",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
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