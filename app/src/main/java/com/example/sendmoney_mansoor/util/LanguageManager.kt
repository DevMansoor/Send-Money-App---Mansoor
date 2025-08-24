package com.example.sendmoney_mansoor.util

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.content.edit
import com.example.sendmoney_mansoor.data.model.LocalizedString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageManager @Inject constructor(
    private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val _currentLanguage = mutableStateOf(getSavedLanguage())
    val currentLanguage: State<String> = _currentLanguage

    fun setLanguage(languageCode: String) {
        sharedPreferences.edit { putString("language", languageCode) }
        _currentLanguage.value = languageCode
    }

    private fun getSavedLanguage(): String {
        return sharedPreferences.getString("language", "en") ?: "en"
    }

    fun getLocalizedString(localizedString: Any?): String {
        return when (localizedString) {
            is LocalizedString -> if (_currentLanguage.value == "ar") localizedString.ar else localizedString.en
            is String -> localizedString
            else -> ""
        }
    }

    fun getLayoutDirection(): LayoutDirection {
        return if (_currentLanguage.value == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr
    }
}