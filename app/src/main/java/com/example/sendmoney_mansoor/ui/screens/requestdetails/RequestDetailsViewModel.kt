package com.example.sendmoney_mansoor.ui.screens.requestdetails

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sendmoney_mansoor.data.repository.SavedRequest
import com.example.sendmoney_mansoor.data.repository.SendMoneyRepository
import com.example.sendmoney_mansoor.util.LanguageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestDetailsViewModel @Inject constructor(
    private val repository: SendMoneyRepository,
    val languageManager: LanguageManager
) : ViewModel() {

    val request = mutableStateOf<SavedRequest?>(null)

    fun loadRequest(requestId: String) {
        viewModelScope.launch {
            request.value = repository.getRequestById(requestId)
        }
    }
}