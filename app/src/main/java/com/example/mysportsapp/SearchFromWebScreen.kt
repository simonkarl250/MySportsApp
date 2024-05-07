package com.example.mysportsapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchClubsFromWeb(searchResults: List<Club>?, onSearchClicked: (String) -> Unit, goBackClicked:()->Unit) {
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
                        "Search From Web",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { goBackClicked() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
    ){values ->
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

                Spacer(modifier = Modifier.width(10.dp))

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
                    TeamCardImages(team = team)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }


            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}




@Composable
fun showSearchClubsFromWebScreen(navController: NavController){
    var searchResults by remember { mutableStateOf<List<Club>>(emptyList()) }

    SearchClubsFromWeb(
        searchResults = searchResults,
        onSearchClicked = {
            // Launch a coroutine to call retrieveClubsByLeague
            CoroutineScope(Dispatchers.Main).launch {
                // Retrieve clubs by league name
                val Data = SearchWeb(it)

                Log.d("clubData", "jsut data: $Data")


                // Process club data (parse JSON, display clubs, etc.)
                if (Data != null) {
                    searchResults = parseJsonToTeamList(Data)

//                    clubData= parseJsonToTeamList(Data ?: "")
                    // Handle successful retrieval

                    Log.d("clubData", "Club3 data: $searchResults")


                    //display the clubdata on this scrreen

                } else {
                    // Handle error
                    Log.e("RetrieveClubs", "Error retrieving club data")
                }
            }

        }, goBackClicked = {
            navController.popBackStack()
        }
    )


}
@Composable
fun TeamCardImages(team: Club) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Display team Jersey image
            val logoUrl = team.teamJersey
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
                    color = Color.Red
                )
            }
        }
    }
}