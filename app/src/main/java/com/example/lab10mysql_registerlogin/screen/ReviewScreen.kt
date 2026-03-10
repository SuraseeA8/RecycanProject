package com.example.lab10mysql_registerlogin.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lab10mysql_registerlogin.data.model.ReviewRequest
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel

@Composable
fun ReviewScreen(
    transactionId: Int,
    buyerId: Int,
    navController: NavController,
    viewModel: RecycanViewModel
) {
    val context = LocalContext.current
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF81C784))
                .padding(vertical = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("รีวิวผู้ขาย", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ดาว
        Text("ให้คะแนน", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            (1..5).forEach { star ->
                Text(
                    text = if (star <= rating) "⭐" else "☆",
                    fontSize = 40.sp,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { rating = star }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Comment
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("ความคิดเห็น (ไม่บังคับ)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ปุ่มส่งรีวิว
        Button(
            onClick = {
                if (rating == 0) {
                    Toast.makeText(context, "กรุณาให้คะแนนก่อน", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                viewModel.submitReview(
                    ReviewRequest(
                        rating = rating,
                        comment = comment,
                        reviewer_id = buyerId,
                        transaction_id = transactionId
                    )
                ) {
                    Toast.makeText(context, "รีวิวสำเร็จ ขอบคุณ!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(50)
        ) {
            Text("ส่งรีวิว", color = Color.White, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ปุ่มข้าม
        TextButton(onClick = { navController.popBackStack() }) {
            Text("ข้ามไปก่อน", color = Color.Gray, fontSize = 16.sp)
        }
    }
}