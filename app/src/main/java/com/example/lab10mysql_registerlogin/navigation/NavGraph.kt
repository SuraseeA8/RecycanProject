package com.example.lab10mysql_registerlogin.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lab10mysql_registerlogin.screen.EditDeleteScreen
import com.example.lab10mysql_registerlogin.screen.ListScreen
import com.example.lab10mysql_registerlogin.screen.ListSoldScreen
import com.example.lab10mysql_registerlogin.screen.LoginScreen
import com.example.lab10mysql_registerlogin.screen.ProfileScreen
import com.example.lab10mysql_registerlogin.screen.RegisterScreen
import com.example.lab10mysql_registerlogin.screen.SellWasteDetailScreen
import com.example.lab10mysql_registerlogin.screen.SellWasteScreen
import com.example.lab10mysql_registerlogin.screen.WastePriceCalculator
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import com.example.lab6.FavoriteScreen
import com.example.lab6.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {

    val recycanViewModel: RecycanViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Profile.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(navController, recycanViewModel)
        }


        composable(Screen.Register.route) {
            RegisterScreen(navController, recycanViewModel)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Screen.WPC.route) {
            WastePriceCalculator(navController, recycanViewModel)
        }

        composable(Screen.SellWaste.route) {
            SellWasteScreen(navController, recycanViewModel)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController, recycanViewModel)
        }

        composable(Screen.Favorite.route) {
            FavoriteScreen(navController, recycanViewModel)
        }

        composable(
            route = Screen.SellWasteDetail.route
        ) { backStackEntry ->
            val id = backStackEntry.arguments
                ?.getString("id")
                ?.toInt() ?: 0
            SellWasteDetailScreen(
                navController = navController,
                viewModel = recycanViewModel,
                categoryId = id
            )
        }

        composable(Screen.List.route) {
            ListScreen(navController, recycanViewModel)
        }

        // ✅ แก้ไขตรงนี้ให้ใช้ route ของ ListSold
        composable(Screen.ListSold.route) {
            ListSoldScreen(navController, recycanViewModel)
        }

        composable(
            route = Screen.EditDeleteScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: 0

            EditDeleteScreen(
                navController = navController,
                viewModel = recycanViewModel,
                listing_id = id
            )
        }

    }
}
