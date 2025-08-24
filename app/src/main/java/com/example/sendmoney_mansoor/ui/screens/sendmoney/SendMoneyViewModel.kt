package com.example.sendmoney_mansoor.ui.screens.sendmoney

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sendmoney_mansoor.data.model.Field
import com.example.sendmoney_mansoor.data.model.Provider
import com.example.sendmoney_mansoor.data.model.Service
import com.example.sendmoney_mansoor.data.repository.SendMoneyRepository
import com.example.sendmoney_mansoor.util.LanguageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val repository: SendMoneyRepository,
    val languageManager: LanguageManager
) : ViewModel() {

    val serviceData = repository.getServiceData()
    val selectedService = mutableStateOf(serviceData.services.firstOrNull())
    val selectedProvider = mutableStateOf(selectedService.value?.providers?.firstOrNull())
    val formData = mutableStateMapOf<String, String>()
    val errorMessages = mutableStateMapOf<String, String>()

    fun onServiceSelected(service: Service) {
        selectedService.value = service
        selectedProvider.value = service.providers.firstOrNull()
        formData.clear()
        errorMessages.clear()
    }

    fun onProviderSelected(provider: Provider) {
        selectedProvider.value = provider
        formData.clear()
        errorMessages.clear()
    }

    fun onValueChange(field: Field, value: String) {
        formData[field.name] = value
        val isValid = validateField(field, value)
        errorMessages[field.name] =
            if (!isValid) languageManager.getLocalizedString(field.validationErrorMessage) else ""
    }

    private fun validateField(field: Field, value: String): Boolean {
        return when {
            field.validationErrorMessage != "" -> {
                when (field.name) {
                    "amount" -> {
                        val doubleValue = value.toDoubleOrNull()
                        doubleValue != null && doubleValue > 0 && value.isNotEmpty()
                    }

                    "bank_account_number" -> {
                        value.matches(Regex(field.validation)) && value.trim().isNotEmpty()
                    }

                    "msisdn" -> {
                        value.matches(Regex(field.validation))
                    }

                    "firstname", "lastname", "full_name" -> {
                        value.trim().isNotEmpty()
                    }

                    "date_of_birth" -> {
                        value.matches(Regex(field.validation)) && value.isNotEmpty()
                    }

                    else -> {
                        if (field.validation.isNotEmpty()) {
                            value.matches(Regex(field.validation))
                        } else {
                            true
                        }
                    }
                }
            }

            else -> {
                if (field.validation.isNotEmpty()) {
                    value.matches(Regex(field.validation))
                } else {
                    true
                }
            }
        }
    }

    fun validateAndSave(onSuccess: () -> Unit) {
        val newErrorMessages = mutableMapOf<String, String>()
        selectedProvider.value?.requiredFields?.forEach { field ->
            val value = formData[field.name] ?: ""
            val isValid = validateField(field, value)
            if (!isValid) {
                newErrorMessages[field.name] =
                    languageManager.getLocalizedString(field.validationErrorMessage)
            }
        }

        errorMessages.putAll(newErrorMessages)

        if (newErrorMessages.isEmpty()) {
            selectedService.value?.let { service ->
                selectedProvider.value?.let { provider ->
                    repository.saveRequest(
                        serviceName = languageManager.getLocalizedString(service.label),
                        providerName = provider.name,
                        amount = formData["amount"] ?: "0.00",
                        data = formData
                    )
                    onSuccess()
                }
            }
        }
    }
}