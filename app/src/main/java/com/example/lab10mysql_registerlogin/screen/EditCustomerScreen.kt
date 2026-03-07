package com.example.recycanproject


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lab10mysql_registerlogin.R
import com.example.lab10mysql_registerlogin.navigation.Screen
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import kotlinx.coroutines.launch

@Composable
fun EditCustomerScreen(navController: NavHostController, viewModel: RecycanViewModel) {

    val user = viewModel.currentUser.value

    var name by remember { mutableStateOf(user?.user_name ?: "") }
    var phone by remember { mutableStateOf(user?.user_phone ?: "") }
    var email = user?.user_email ?: ""


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        CustomerEditProfileTopBar(navController)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            CustomerEditProfileSection(
                name = name,

                phone = phone,
                onNameChange = { name = it },

                onPhoneChange = { phone = it },
                viewModel = viewModel
            )

            Spacer(modifier = Modifier.height(60.dp))

            CustomerConfirmButton(
                name = name,
                phone = phone,
                email = email,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}


@Composable
fun CustomerEditProfileTopBar(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .background(Color(0xFF7DBB7D))
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.back),
            contentDescription = "Back",
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.CenterStart)
                .clickable {
                    navController.navigate(Screen.HomeCustomerScreen.route)
                }
        )



        Text(
            text = "แก้ไขโปรไฟล์",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}








@Composable
fun CustomerEditProfileSection(
    name: String,
    phone: String,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    viewModel: RecycanViewModel,) {

    val user = viewModel.currentUser.value



    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = "http://192.168.1.10:3000/images/${user?.user_image}"


        // รูปโปรไฟล์
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            placeholder = painterResource(R.drawable.default_profile),
            error = painterResource(R.drawable.default_profile),
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(

            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            // ชื่อ
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("ชื่อ - นามสกุล") },
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(15.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Color.Gray,

                )
        }
        Spacer(modifier = Modifier.height(12.dp))



        Row(

            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // โทรศัพท์
            OutlinedTextField(
                value = phone,
                onValueChange = onPhoneChange,
                label = { Text("เบอร์โทรศัพท์") },
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(15.dp),
                leadingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {

                        Image(
                            painter = painterResource(R.drawable.th_flag),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                                .padding(2.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text("+66")
                    }
                }
            )






            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                modifier = Modifier.size(20.dp).fillMaxSize(),
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Color.Gray,
            )
        }





        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Email (แก้ไม่ได้)
            OutlinedTextField(
                value = user?.user_email ?: "",
                onValueChange = {},
                enabled = false,
                label = { Text("อีเมล") },
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(15.dp)
            )

            Spacer(modifier = Modifier.height(60.dp))
        }

    }
}
















@Composable
fun CustomerConfirmButton(
    name: String,
    email: String,
    phone: String,
    viewModel: RecycanViewModel,
    navController: NavController
) {

    val context = LocalContext.current

    Button(
        onClick = {

            viewModel.updateCustomerProfile(name, email, phone)

            Toast.makeText(
                context,
                "แก้ไขโปรไฟล์สำเร็จ",
                Toast.LENGTH_SHORT
            ).show()

            navController.navigate(Screen.HomeCustomerScreen.route) {
                popUpTo(Screen.HomeCustomerScreen.route) { inclusive = true }
            }
        },

        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(50.dp),

        shape = RoundedCornerShape(10.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4CAF50)
        )
    ) {

        Text(
            text = "ยืนยัน",
            color = Color.White,
            fontSize = 18.sp
        )
    }
}