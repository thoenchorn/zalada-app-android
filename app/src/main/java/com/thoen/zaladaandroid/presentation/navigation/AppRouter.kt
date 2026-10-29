package com.thoen.zaladaandroid.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thoen.zaladaandroid.presentation.authentication.CreatePasswordScreen
import com.thoen.zaladaandroid.presentation.authentication.ForgetPasswordScreen
import com.thoen.zaladaandroid.presentation.authentication.LoginScreen
import com.thoen.zaladaandroid.presentation.authentication.OTPScreen
import com.thoen.zaladaandroid.presentation.authentication.SignUpScreen
import com.thoen.zaladaandroid.presentation.cart.CartScreen
import com.thoen.zaladaandroid.presentation.home.DetailsProductScreen
import com.thoen.zaladaandroid.presentation.home.HomeScreen
import com.thoen.zaladaandroid.presentation.home.ProductAllScreen
import com.thoen.zaladaandroid.presentation.main.MainScreen
import com.thoen.zaladaandroid.presentation.search.SearchScreen

@Composable
fun AppRouter() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Login.route,
        builder = {
            composable(Login.route) {
                LoginScreen(navController)
            }
            composable(Signup.route) {
                SignUpScreen(navController)
            }
            composable(Main.route) {
                MainScreen(navController)
            }
            composable(Home.route) {
                HomeScreen(navController)
            }
            composable(Cart.route) {
                CartScreen(navController)
            }
            composable(
                route = DetailProduct.routeWithArgs,
                arguments = listOf(navArgument(DetailProduct.productIdArg) { type = NavType.IntType }),
            ) { backStackEntry ->
                DetailsProductScreen(
                    navController = navController,
                    productId = backStackEntry.arguments?.getInt(DetailProduct.productIdArg)
                        ?: 0,
                )
            }
            composable(Search.route) {
                SearchScreen(navController)
            }
            composable(ProductAll.route) {
                ProductAllScreen(navController)
            }
            composable(ForgetPassword.route) {
                ForgetPasswordScreen(navController)
            }
            composable(OTP.route) {
                OTPScreen(navController)
            }
            composable(CreatePassword.route) {
                CreatePasswordScreen(navController)
            }
        }
    )
}