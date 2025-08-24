package com.example.sendmoney_mansoor.util

import android.content.Context
import androidx.annotation.RawRes
import java.io.BufferedReader
import java.io.InputStreamReader

fun getJsonDataFromRaw(context: Context, @RawRes resId: Int): String {
    return try {
        val inputStream = context.resources.openRawResource(resId)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val jsonString = reader.use { it.readText() }
        inputStream.close()
        jsonString
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}