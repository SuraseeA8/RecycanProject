package com.example.lab10mysql_registerlogin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.MyTopAppBar
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.utils.SharedPreferencesManager
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel

private val TopGreen  = Color(0xFF81C784)
private val CardGreen = Color(0xFFB6D9B8)
private val TitleGreen = Color(0xFF2E7D32)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellWasteScreen(
    navController: NavController,
    viewModel: RecycanViewModel = viewModel()
) {
    val categories = viewModel.categoryList
    val context = LocalContext.current
    val prefs = SharedPreferencesManager(context)

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    Scaffold(
        topBar = {
            MyTopAppBar(title = "ขายขยะ", navController = navController)
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // หัวข้อ
            Text(
                text = "เลือกประเภทขยะ",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = TitleGreen,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal
            )

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(categories) { category ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(8.dp, RoundedCornerShape(5.dp), clip = false)
                            .clickable {
                                navController.navigate(
                                    Screen.SellWasteDetail.createRoute(category.category_id)
                                )
                            },
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
                                    model = "http://10.0.2.2:3000/uploads/${category.image_url}",
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(6.dp)
                                )
                            }

                            Spacer(Modifier.width(12.dp))

                            Text(
                                text = category.category_name,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF1B1B1B)
                            )

                            Text(
                                text = "${"%.2f".format(category.price_per_kg)} บาท/กก.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF1B1B1B)
                            )
                        }
                    }
                }
            }
        }
    }
}