package com.example.lab10mysql_registerlogin.navigation

sealed class Screen(val route: String, val name: String) {

    object FirstScreen : Screen("first_screen","Register")
    data object Login : Screen(
        route = "login_screen",
        name = "Login"
    )

    data object Register : Screen(
        route = "register_screen",
        name = "Register"
    )

    data object Review : Screen(
        route = "review/{transactionId}/{buyerId}",
        name = "Review"
    ) {
        fun createRoute(transactionId: Int, buyerId: Int) = "review/$transactionId/$buyerId"
    }

    data object ReviewList : Screen(
        route = "review_list/{sellerId}",
        name = "ReviewList"
    ) {
        fun createRoute(sellerId: Int) = "review_list/$sellerId"
    }

    data object HomeSellerScreen : Screen("homesell_screen", "HomeSeller")
    data object HomeCustomerScreen : Screen("homecus_screen", "HomeCustomer")

    data object ProfileSellerScreen : Screen("profilesell_screen", "ProfileSeller")
    data object ProfileCustomerScreen : Screen("profilecus_screen", "ProfileCustomer")

    data object EditSellerScreen : Screen("editsell_screen", "EditSeller")
    data object EditCustomerScreen : Screen("editcus_screen", "EditCustomer")

    data object Profile : Screen(
        route = "profile_screen",
        name = "Profile"
    )

    data object WPC : Screen(
        route = "WastePriceCalculator_screen",
        name = "WastePriceCalculator"
    )

    data object SellWaste : Screen(route = "SellWaster_screen", name = "SellWaster")
    data object Home : Screen(route = "home_screen", name = "Home")
    data object Favorite : Screen(route = "Favorite_screen", name = "Favorite")

    data object SellWasteDetail : Screen(
        "sellDetail/{id}",
        "SellWasteDetail"
    ) {
        fun createRoute(id: Int): String {
            return "sellDetail/$id"
        }
    }

    data object List : Screen(route = "List_screen", name = "List")

    data object EditDeleteScreen : Screen(
        route = "EditDelete_screen/{id}",
        name = "EditDelete"
    )

    data object History : Screen(
        route = "history_screen",
        name = "History"
    )

    data object HistoryDetail : Screen(
        route = "history_detail/{id}",
        name = "HistoryDetail"
    ) {
        fun createRoute(id: Int): String {
            return "history_detail/$id"
        }
    }

    // ===== BUYER FLOW =====
    data object BuyerCategory : Screen("buyer_category", "BuyerCategory")

    data object BuyerListing : Screen("buyer_listing/{category_id}", "BuyerListing") {
        fun createRoute(categoryId: Int) = "buyer_listing/$categoryId"
    }

    data object BuyerDetail : Screen("buyer_detail/{listing_id}", "BuyerDetail") {
        fun createRoute(listingId: Int) = "buyer_detail/$listingId"
    }

    data object WasteList : Screen("waste_list", "WasteList")

    data object UpdateStatus : Screen("update_status/{listing_id}", "UpdateStatus") {
        fun createRoute(listingId: Int) = "update_status/$listingId"
    }

    // ===== PURCHASE HISTORY =====
    data object PurchaseHistory : Screen("purchase_history", "PurchaseHistory")

    data object PurchaseDetail : Screen("purchase_detail/{id}", "PurchaseDetail") {
        fun createRoute(id: Int) = "purchase_detail/$id"
    }
}