package com.example.lab10mysql_registerlogin.screen

import com.example.lab10mysql_registerlogin.data.model.BuyerUiState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.viewmodel.CategoryVM

private val TopGreen = Color(0xFF81C784)
private val CardGreen = Color(0xFFB6D9B8)
val TitleGreen = Color(0xFF2E7D32)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerCategoryScreen(
    onClickCategory: (Int) -> Unit,
    onBackToMain: () -> Unit = {},
    vm: CategoryVM = viewModel()
) {
    LaunchedEffect(Unit) { vm.load() }
    val state by vm.state.collectAsState()

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
                            onClick = { onBackToMain() },
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
                text = "เลือกประเภทขยะ",
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = TitleGreen,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal
            )

            Spacer(Modifier.height(12.dp))

            when (val s = state) {
                is BuyerUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                        CircularProgressIndicator(color = TitleGreen)
                    }
                }
                is BuyerUiState.Error -> {
                    Text(
                        s.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
                is BuyerUiState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(s.data) { c ->
                            CategoryRowCard(
                                name = c.category_name,
                                pricePerKg = "%.2f".format(c.price_per_kg),
                                imageUrl = c.image_url,
                                onClick = { onClickCategory(c.category_id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryRowCard(
    name: String,
    pricePerKg: String,
    imageUrl: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .shadow(8.dp, RoundedCornerShape(5.dp), clip = false)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = CardGreen),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = toFullImageUrl(imageUrl),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(6.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text = name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF1B1B1B)
            )

            Text(
                text = "$pricePerKg บาท/กก.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF1B1B1B)
            )
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