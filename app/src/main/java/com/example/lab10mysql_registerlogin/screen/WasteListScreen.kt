package com.example.lab10mysql_registerlogin.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lab10mysql_registerlogin.R
import com.example.lab10mysql_registerlogin.data.api.RecycanClient
import com.example.lab10mysql_registerlogin.data.model.BuyerListingModel
import com.example.lab10mysql_registerlogin.data.model.BuyerListingResponse
import com.example.lab10mysql_registerlogin.navigation.Screen

@Composable
fun WasteListingScreen(
    navController: NavController,
    onSelectListing: (BuyerListingModel) -> Unit
) {
    var listings by remember { mutableStateOf<List<BuyerListingModel>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        RecycanClient.recycanAPI.getBuyerListings()
            .enqueue(object : Callback<BuyerListingResponse> {
                override fun onResponse(
                    call: Call<BuyerListingResponse>,
                    response: Response<BuyerListingResponse>
                ) {
                    loading = false
                    listings = response.body()?.data
                        ?.filter { it.transaction_state != "success" }
                        ?: emptyList()
                }

                override fun onFailure(call: Call<BuyerListingResponse>, t: Throwable) {
                    loading = false
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
                .padding(vertical = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "รายการขยะ",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ทั้งหมด ${listings.size} รายการ",
            modifier = Modifier.padding(start = 20.dp),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("กำลังโหลดข้อมูล...")
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(listings) { item ->
                    val imageName = when (item.category_name) {
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

                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .background(Color(0xFFAED0AE), RoundedCornerShape(16.dp))
                                    .align(Alignment.CenterStart),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = "http://10.0.2.2:3000/uploads/$imageName",
                                    contentDescription = null,
                                    modifier = Modifier.size(45.dp)
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .padding(start = 84.dp, end = 0.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    item.category_name,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "น้ำหนัก ${item.weight} กิโลกรัม",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Text("ราคา ${item.price} บาท", fontSize = 14.sp)
                                }
                            }

                            Text(
                                text = "อัปเดต",
                                color = Color(0xFF2962FF),
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .clickable { onSelectListing(item) }
                            )
                        }
                    }
                }
            }
        }

        // Bottom Navigation
        WasteListBottomNavBar(navController)
    }
}

@Composable
fun WasteListBottomNavBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .height(150.dp),
        containerColor = Color(0xFF4CAF50),
        tonalElevation = 0.dp
    ) {

        val items = listOf(
            Triple(Screen.HomeCustomerScreen.route, R.drawable.home, "Home"),
            Triple(Screen.WasteList.route, R.drawable.salelist, "List"),
            Triple(Screen.EditCustomerScreen.route, R.drawable.profile, "Profile")
        )

        items.forEach { (route, icon, label) ->

            val isSelected = currentRoute == route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = null,
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
                icon = {

                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 65.dp else 55.dp)
                            .background(
                                if (isSelected) Color(0xFF2E7D33)
                                else Color(0xFF81C784),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(icon),
                            contentDescription = label,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                }
            )
        }
    }
}