package com.example.mysportsapp

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(onAddLeaguesClicked: () -> Unit, onSearchByLeagueClicked:()-> Unit, onSearchClubsClicked: () -> Unit,onSearchClubsFromService:()->Unit) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(Color.Blue),


        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "My Sport App HomePage",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {values->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan)
                .padding(values),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            OutlinedButton(onClick = onAddLeaguesClicked,
                modifier = Modifier.fillMaxWidth()
                ) {
                Text(text = "Add Leagues to DB", color = Color.Unspecified)
            }
            ElevatedButton(onClick = onSearchByLeagueClicked,
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Search And Save Clubs", color = Color.Unspecified)
            }
            FilledTonalButton(onClick = onSearchClubsClicked,
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Search Saved Clubs From DB ", color = Color.Unspecified)
            }
            Button(onClick = onSearchClubsFromService,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                Text(text = "Search Clubs From Web Service ", color = Color.Unspecified)
            }

        }
    }
}

@Composable
fun MyApp(navController: NavController) {

    val context = LocalContext.current

    HomeScreen(
            onAddLeaguesClicked = {
                // ImplementING logic to add leagues to the databasE
                Toast.makeText(context, "Add Leagues to DB clicked", Toast.LENGTH_SHORT).show()
//                val database = Room.databaseBuilder(
//                    context,
//                    AppDatabase::class.java, "football-leagues-db"
//                ).fallbackToDestructiveMigration().build()
                val database = AppDatabase.getDatabase(context)
                val leagues = listOf(
                    FootballLeague(
                        "4328".toInt(),
                        "English Premier League",
                        "Soccer",
                        "Premier League, EPL"
                    ),
                    FootballLeague("4329".toInt(), "English League Championship", "Soccer", "Championship")
                    // WE WILL Add more leagues here
                )

                runBlocking(Dispatchers.IO) {
                    database.footballLeagueDao().insertAll(leagues)
                }
                Toast.makeText(context, "Various Football leagues added", Toast.LENGTH_SHORT).show()

            },
            onSearchByLeagueClicked = {
                // Implement logic to display SearchClubsScreen which enables user to search teams by league

                // Navigate to the SearchClubsScreen
//                navigateToSearchClubsScreen = true
                navController.navigate(route = AppNavigation.SearchAndSave.route)


//
//                Toast.makeText(context, "Search for Clubs By League clicked", Toast.LENGTH_SHORT)
//                    .show()
            },
            onSearchClubsClicked = {
//                navigateToSearchForClubs = true;
                // Implementation of  logic to search clubs
//                Toast.makeText(context, "Search for Clubs clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(route = AppNavigation.SearchClubs.route)
            },
            onSearchClubsFromService ={
                navController.navigate(route = AppNavigation.SearchFromWeb.route)
            }
        )
//    }
}
