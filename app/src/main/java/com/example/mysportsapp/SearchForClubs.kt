package com.example.mysportsapp
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchClubsByNameScreen(searchResults: List<Club>?,onSearchClicked: (String) -> Unit,goBackClicked:()->Unit) {
    var searchText by remember { mutableStateOf("") }


    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Search From Database",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { goBackClicked() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ){values->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search by Club or League Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onSearchClicked(searchText) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Search")
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(searchResults ?: emptyList()) { team ->
                    TeamCardModified(team = team)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }


            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun TeamCardModified(team: Club) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Display team logo image
            val logoUrl = team.teamLogo // Assuming team.teamLogo contains the URL of the logo
            Log.d("8uiuy", "TeamCardModified: "+ logoUrl.isNotBlank())
            if (logoUrl.isNotBlank()) {
                AsyncImage(
                    model = logoUrl,
                    contentDescription = null,
                )

            } else {
                // Display a placeholder if no logo URL is available
                Text(
                    text = "No logo available",
                    color = androidx.compose.ui.graphics.Color.Red
                )
            }
            // Display team information
            Text(text = "ID: ${team.idTeam}")
            Text(text = "Name: ${team.name}")
            Text(text = "Short Name: ${team.shortName}")
            Text(text = "Alternate Name: ${team.alternateName}")
            Text(text = "Formed Year: ${team.formedYear}")
            Text(text = "League: ${team.league}")
            Text(text = "League ID: ${team.idLeague}")
            Text(text = "Stadium: ${team.stadium}")
            Text(text = "Keywords: ${team.keywords}")
            Text(text = "Stadium Thumbnail: ${team.stadiumThumb}")
            Text(text = "Stadium Location: ${team.stadiumLocation}")
            Text(text = "Stadium Capacity: ${team.stadiumCapacity}")
            Text(text = "Website: ${team.website}")
            Text(text = "Team Jersey: ${team.teamJersey}")
            Text(text = "Team Logo: ${team.teamLogo}")


        }
    }
}


@Composable
fun showSearchClubsScreen(navController: NavController) {
    var searchResults by remember { mutableStateOf<List<Club>>(emptyList()) }
    val context = LocalContext.current

    SearchClubsByNameScreen(
        searchResults = searchResults,
        onSearchClicked = {
            val database = AppDatabase.getDatabase(context)

            runBlocking(Dispatchers.IO) {
                val searchString = it // Example search string entered by the user
                Log.d("rere", "showSearchClubsScreen:$searchString ")
                // Perform the search operation on the database
                val dbSearchResults = database.clubDao().searchClubs(searchString)

                // Handle the search results
                if (dbSearchResults.isNotEmpty()) {
                    searchResults = dbSearchResults
                } else {
                    // Handle the case where no search results are found
                    Log.d("SearchResult", "No clubs found matching the search criteria")
                }
            }



        }, goBackClicked = {
            navController.popBackStack()
        }
    )
}

