package com.example.lab10mysql_registerlogin.navigation

sealed class Screen(val route: String, val name: String) {

    data object Login : Screen(
        route = "login_screen",
        name = "Login"
    )

    data object Register : Screen(
        route = "register_screen",
        name = "Register"
    )

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

    object SellWasteDetail : Screen(
        "sellDetail/{id}",
        "SellWasteDetail"
    ) {
        fun createRoute(id: Int): String {
            return "sellDetail/$id"
        }
    }



}