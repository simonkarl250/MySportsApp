package com.example.mysportsapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun setUpNavGraph(
    navController: NavHostController
){
 NavHost(navController = navController,
     startDestination = AppNavigation.Home.route ) {
        composable(
            route = AppNavigation.Home.route
        ){
            MyApp(navController)
        }

         composable(
             route = AppNavigation.SearchAndSave.route
         ){

             showSearchScreen(navController)
         }

     composable(
         route = AppNavigation.SearchClubs.route
     ){

         showSearchClubsScreen(navController)
     }
     composable(
         route = AppNavigation.SearchFromWeb.route
     ){

         showSearchClubsFromWebScreen(navController)
     }


 }
}