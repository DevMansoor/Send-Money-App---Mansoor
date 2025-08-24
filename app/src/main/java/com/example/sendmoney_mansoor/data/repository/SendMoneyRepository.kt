package com.example.sendmoney_mansoor.data.repository

import android.content.Context
import android.util.Log
import com.example.sendmoney_mansoor.R
import com.example.sendmoney_mansoor.data.model.LocalizedString
import com.example.sendmoney_mansoor.data.model.ServiceData
import com.example.sendmoney_mansoor.util.getJsonDataFromRaw
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

data class SavedRequest(
    val id: String,
    val serviceName: String,
    val providerName: String,
    val amount: String,
    val data: Map<String, String>
)

class SendMoneyRepository(private val context: Context) {
    private val _savedRequests = MutableStateFlow<List<SavedRequest>>(emptyList())
    val savedRequests: StateFlow<List<SavedRequest>> = _savedRequests

    fun getServiceData(): ServiceData {
        val jsonString = getJsonDataFromRaw(context, R.raw.services)
        return try {
            if (jsonString.isBlank()) {
                throw IllegalStateException("JSON file is empty or not found")
            }
            Gson().fromJson(jsonString, ServiceData::class.java) ?: throw IllegalStateException("Failed to parse JSON")
        } catch (e: Exception) {
            e.printStackTrace()
            ServiceData(
                title = LocalizedString("Send Money", "إرسال الأموال"),
                services = emptyList()
            )
        }
    }

    fun saveRequest(
        serviceName: String,
        providerName: String,
        amount: String,
        data: Map<String, String>
    ) {
        val request = SavedRequest(
            id = UUID.randomUUID().toString(),
            serviceName = serviceName,
            providerName = providerName,
            amount = amount,
            data = data
        )
        _savedRequests.value += request
        Log.d("SendMoneyRepository", "Saved request: $request")
    }

    fun getRequestById(requestId: String): SavedRequest? {
        return _savedRequests.value.find { it.id == requestId }
    }
}