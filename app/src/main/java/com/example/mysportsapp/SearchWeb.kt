package com.example.mysportsapp

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

suspend fun SearchWeb(searchString: String): String? {
    // Construct the URL with the user-entered league name
    val baseUrl = "https://www.thesportsdb.com/api/v1/json/3/searchteams.php?"
    val params = "t=$searchString"
    val urlString = "$baseUrl$params"

    return withContext(Dispatchers.IO) {
        var response: String? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            response = stringBuilder.toString()
        } catch (e: Exception) {
            Log.e("RetrieveClubs", "Error retrieving clubs", e)
        } finally {
            connection?.disconnect()
        }
        response
    }
}