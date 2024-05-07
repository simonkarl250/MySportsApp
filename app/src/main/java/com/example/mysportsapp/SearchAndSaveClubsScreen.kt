package com.example.mysportsapp

import android.content.Context
import android.util.Log
import android.widget.Button
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchClubsScreen(clubData: List<Club>?, onRetrieveClubsClicked: (String) -> Unit, onSaveClubsClicked: () -> Unit,goBackClicked:()->Unit) {
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
                        "Search And Save",
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

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Enter league name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(13.dp))

            Row {
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { onRetrieveClubsClicked(searchText) }) {
                    Text(text = "Retrieve Clubs")

                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = { onSaveClubsClicked() }) {
                    Text(text = "Save Clubs")
                }
            }
            Log.d("RetnnnnnnnnnrieveClubs", "Club1 data: $clubData")

            // Display the team data if available in a LazyColumn
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(clubData ?: emptyList()) { team ->
                    TeamCard(team = team)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun TeamCard(team: Club) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
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
            // Add more Text composables for other properties
        }
    }
}


@Composable
fun showSearchScreen(navController: NavController) {
    val context = LocalContext.current
    // Initialize clubData state
    var clubData by remember { mutableStateOf<List<Club>?>(null) } // Change the type to List<Team>?

    SearchClubsScreen(
        clubData = clubData,


        onSaveClubsClicked= {


            val database = AppDatabase.getDatabase(context)


            runBlocking(Dispatchers.IO) {
                val dataToInsert = clubData ?: emptyList() // Provide a non-null default value
                Log.d("jjj", "showSearchScreen: $dataToInsert")
                database.clubDao().insertAll(dataToInsert)
            }

        },
        onRetrieveClubsClicked = {
            // i want to use searchText here
            Log.d("SearchScreen", "Search text: $it")

            // Launch a coroutine to call retrieveClubsByLeague
            CoroutineScope(Dispatchers.Main).launch {
                // Retrieve clubs by league name
                val Data = retrieveClubsByLeague(it)

                Log.d("clubData", "jsut data: $Data")


                // Process club data (parse JSON, display clubs, etc.)
                if (Data != null) {
                   clubData = parseJsonToTeamList(Data)

//                    clubData= parseJsonToTeamList(Data ?: "")
                    // Handle successful retrieval

                    Log.d("clubData", "Club3 data: $clubData")


                    //display the clubdata on this scrreen

                } else {
                    // Handle error
                    Log.e("RetrieveClubs", "Error retrieving club data")
                }
            }
        },
        goBackClicked = {
                navController.popBackStack()

        }

    )

}

fun parseJsonToTeamList(jsonString: String): List<Club> {
    val teamList = mutableListOf<Club>()
    try {
        val jsonObject = JSONObject(jsonString)
        val teamsArray = jsonObject.getJSONArray("teams")
        for (i in 0 until teamsArray.length()) {
            val teamObject = teamsArray.getJSONObject(i)
            val idTeam = teamObject.getString("idTeam")
            val name = teamObject.getString("strTeam")
            val shortName = teamObject.getString("strTeamShort")
            val alternateName = teamObject.getString("strAlternate")
            val formedYear = teamObject.getString("intFormedYear")
            val league = teamObject.getString("strLeague")
            val idLeague = teamObject.getString("idLeague")
            val stadium = teamObject.getString("strStadium")
            val keywords = teamObject.getString("strKeywords")
            val stadiumThumb = teamObject.getString("strStadiumThumb")
            val stadiumLocation = teamObject.getString("strStadiumLocation")
            val stadiumCapacity = teamObject.getString("intStadiumCapacity")
            val website = teamObject.getString("strWebsite")
            val teamJersey = teamObject.getString("strTeamJersey")
            val teamLogo = teamObject.getString("strTeamLogo")
            val team = Club(
                idTeam.toInt(), name, shortName, alternateName, formedYear, league, idLeague,
                stadium, keywords, stadiumThumb, stadiumLocation, stadiumCapacity,
                website, teamJersey, teamLogo
            )
            teamList.add(team)
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return teamList
}



//data class Team(
//     val idTeam: Int,
//    val name: String,
//  val shortName: String,
//   val alternateName: String,
//   val formedYear: String,
//   val league: String,
//    val idLeague: String,
//    val stadium: String,
//   val keywords: String,
//   val stadiumThumb: String,
//   val stadiumLocation: String,
//   val stadiumCapacity: String,
// val website: String,
//  val teamJersey: String,
//   val teamLogo: String
//)

