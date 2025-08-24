package com.example.sendmoney_mansoor.ui.screens.login

import androidx.lifecycle.ViewModel
import com.example.sendmoney_mansoor.data.model.LocalizedString
import com.example.sendmoney_mansoor.util.LanguageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val languageManager: LanguageManager
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun updateUsername(newUsername: String) = _username.tryEmit(newUsername)
    fun updatePassword(newPassword: String) = _password.tryEmit(newPassword)

    fun login(onSuccess: () -> Unit) {
        if (_username.value == "testuser" && _password.value == "password123") {
            _errorMessage.value = ""
            onSuccess()
        } else {
            _errorMessage.value = languageManager.getLocalizedString(
                LocalizedString("Invalid username or password", "اسم المستخدم أو كلمة المرور غير صالحة")
            )
        }
    }
}