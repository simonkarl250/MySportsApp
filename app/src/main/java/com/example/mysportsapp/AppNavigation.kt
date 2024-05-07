package com.example.mysportsapp

sealed class AppNavigation (val route: String){
    data object Home: AppNavigation(route = "home_screen")
    data object  SearchAndSave:AppNavigation(route = "search_clubs_screen")
    data object  SearchClubs:AppNavigation(route = "search_clubs_by_name_screen")
    data object  SearchFromWeb:AppNavigation(route = "search_from_web")

}