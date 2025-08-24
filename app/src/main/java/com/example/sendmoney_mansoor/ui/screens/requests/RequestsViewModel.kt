package com.example.sendmoney_mansoor.ui.screens.requests

import androidx.lifecycle.ViewModel
import com.example.sendmoney_mansoor.data.repository.SavedRequest
import com.example.sendmoney_mansoor.data.repository.SendMoneyRepository
import com.example.sendmoney_mansoor.util.LanguageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val repository: SendMoneyRepository,
    val languageManager: LanguageManager
) : ViewModel() {

    val requests: StateFlow<List<SavedRequest>> = repository.savedRequests
}