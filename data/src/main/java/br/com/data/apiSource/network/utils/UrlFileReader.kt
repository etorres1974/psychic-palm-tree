package br.com.data.apiSource.network.utils

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection


fun getTextFromWeb(urlString: String?): String {
    val feedUrl: URLConnection
    val placeAddress: MutableList<String?> = ArrayList()
    return try {
        feedUrl = URL(urlString).openConnection()
        val `is`: InputStream = feedUrl.getInputStream()
        val reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
        var line: String? = null
        while (reader.readLine().also { line = it } != null)
        {
            placeAddress.add(line)
        }
        `is`.close() // close input stream
        placeAddress.joinToString(separator = "\n")
    } catch (e: Exception) {
        Log.e("ABACATE", "Web Text Faill ${e}")
        ""
    }
}
