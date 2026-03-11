package com.example.lab10mysql_registerlogin.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lab10mysql_registerlogin.screen.EditDeleteScreen
import com.example.lab10mysql_registerlogin.screen.HistoryDetailScreen
import com.example.lab10mysql_registerlogin.screen.HistoryScreen
import com.example.lab10mysql_registerlogin.screen.ListScreen
import com.example.lab10mysql_registerlogin.screen.LoginScreen
import com.example.lab10mysql_registerlogin.screen.RegisterScreen
import com.example.lab10mysql_registerlogin.screen.SellWasteDetailScreen
import com.example.lab10mysql_registerlogin.screen.SellWasteScreen
import com.example.lab10mysql_registerlogin.screen.WastePriceCalculator
import com.example.lab10mysql_registerlogin.screen.BuyerCategoryScreen
import com.example.lab10mysql_registerlogin.screen.BuyerListingScreen
import com.example.lab10mysql_registerlogin.screen.BuyerDetailScreen
import com.example.lab10mysql_registerlogin.screen.FirstScreen
import com.example.lab10mysql_registerlogin.screen.WasteListingScreen
import com.example.lab10mysql_registerlogin.screen.UpdateStatusScreen
import com.example.lab10mysql_registerlogin.screen.PurchaseHistoryScreen
import com.example.lab10mysql_registerlogin.screen.PurchaseDetailScreen
import com.example.lab10mysql_registerlogin.viewmodel.RecycanViewModel
import com.example.lab6.FavoriteScreen
import com.example.lab6.HomeScreen
import com.example.recycanproject.EditCustomerScreen
import com.example.recycanproject.EditSellerScreen
import com.example.lab10mysql_registerlogin.screen.HomeCustomerScreen
import com.example.lab10mysql_registerlogin.screen.HomeSellerScreen
import com.example.lab10mysql_registerlogin.screen.ReviewListScreen
import com.example.lab10mysql_registerlogin.screen.ReviewScreen

@Composable
fun NavGraph(navController: NavHostController) {

    val recycanViewModel: RecycanViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.FirstScreen.route
    ) {

        composable(Screen.FirstScreen.route) {
            FirstScreen(navController, recycanViewModel)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController, recycanViewModel)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController, recycanViewModel)
        }

        composable(route = Screen.HomeSellerScreen.route) {
            HomeSellerScreen(navController = navController, recycanViewModel, "ผู้ขาย")
        }

        composable(route = Screen.HomeCustomerScreen.route) {
            HomeCustomerScreen(navController = navController, recycanViewModel, "ผู้ซื้อ")
        }

        composable(route = Screen.EditSellerScreen.route) {
            EditSellerScreen(navController = navController, recycanViewModel)
        }

        composable(route = Screen.EditCustomerScreen.route) {
            EditCustomerScreen(navController = navController, recycanViewModel)
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
            route = Screen.ReviewList.route,
            arguments = listOf(navArgument("sellerId") { type = NavType.IntType })
        ) { entry ->
            val sellerId = entry.arguments?.getInt("sellerId") ?: 0
            ReviewListScreen(
                sellerId = sellerId,
                navController = navController,
                viewModel = recycanViewModel
            )
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

        composable(route = Screen.History.route) {
            HistoryScreen(
                navController = navController,
                vm = recycanViewModel
            )
        }

        composable(
            route = Screen.HistoryDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            HistoryDetailScreen(
                navController = navController,
                vm = recycanViewModel,
                transactionId = id
            )
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

        // ===== BUYER FLOW =====
        composable(Screen.BuyerCategory.route) {
            BuyerCategoryScreen(
                onClickCategory = { categoryId ->
                    navController.navigate(Screen.BuyerListing.createRoute(categoryId))
                },
                onBackToMain = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.BuyerListing.route,
            arguments = listOf(navArgument("category_id") { type = NavType.IntType })
        ) { entry ->
            val categoryId = entry.arguments?.getInt("category_id") ?: 0
            BuyerListingScreen(
                category_id = categoryId,
                onClickDetail = { listingId ->
                    navController.navigate(Screen.BuyerDetail.createRoute(listingId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.BuyerDetail.route,
            arguments = listOf(navArgument("listing_id") { type = NavType.IntType })
        ) { entry ->
            val listingId = entry.arguments?.getInt("listing_id") ?: 0
            BuyerDetailScreen(
                listing_id = listingId,
                onBack = { navController.popBackStack() },
                onBuySuccess = {
                    navController.navigate(Screen.WasteList.route) {
                        popUpTo(Screen.BuyerCategory.route)
                    }
                }
            )
        }

        composable(Screen.WasteList.route) {
            WasteListingScreen(
                navController = navController,
                onSelectListing = { listing ->
                    navController.navigate(Screen.UpdateStatus.createRoute(listing.listing_id))
                }
            )
        }



        // แก้ UpdateStatusScreen ให้รับ viewModel และ buyerId
        composable(
            route = Screen.UpdateStatus.route,
            arguments = listOf(navArgument("listing_id") { type = NavType.IntType })
        ) { entry ->
            val listingId = entry.arguments?.getInt("listing_id") ?: 0
            UpdateStatusScreen(
                listingId = listingId,
                navController = navController,
                viewModel = recycanViewModel,          // เพิ่ม
                buyerId = recycanViewModel.currentUser.value?.user_id ?: 0  // เพิ่ม
            )
        }

// เพิ่ม route รีวิว
        composable(
            route = Screen.Review.route,
            arguments = listOf(
                navArgument("transactionId") { type = NavType.IntType },
                navArgument("buyerId") { type = NavType.IntType }
            )
        ) { entry ->
            val transactionId = entry.arguments?.getInt("transactionId") ?: 0
            val buyerId = entry.arguments?.getInt("buyerId") ?: 0
            ReviewScreen(
                transactionId = transactionId,
                buyerId = buyerId,
                navController = navController,
                viewModel = recycanViewModel
            )
        }

        // ===== PURCHASE HISTORY =====
        composable(Screen.PurchaseHistory.route) {
            PurchaseHistoryScreen(navController = navController)
        }

        composable(
            route = Screen.PurchaseDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            PurchaseDetailScreen(
                id = id.toString(),
                navController = navController
            )
        }
    }
}