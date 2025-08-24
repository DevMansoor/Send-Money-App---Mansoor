package com.example.sendmoney_mansoor.ui.screens.sendmoney

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sendmoney_mansoor.data.model.Field
import com.example.sendmoney_mansoor.data.model.LocalizedString
import com.example.sendmoney_mansoor.data.model.Provider
import com.example.sendmoney_mansoor.data.model.Service
import com.example.sendmoney_mansoor.ui.common.CustomDropdown
import com.example.sendmoney_mansoor.ui.common.LanguageSwitcher
import com.example.sendmoney_mansoor.util.LanguageManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(onRequestSaved: () -> Unit) {
    val viewModel = hiltViewModel<SendMoneyViewModel>()
    val languageManager = viewModel.languageManager
    val selectedService by viewModel.selectedService
    val selectedProvider by viewModel.selectedProvider
    val formData = viewModel.formData
    val focusManager = LocalFocusManager.current
    val errorMessages = viewModel.errorMessages
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var backPressedTime by remember { mutableLongStateOf(0L) }

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 2000) {
            (context as? Activity)?.finish()
        } else {
            backPressedTime = currentTime
            Toast.makeText(
                context,
                languageManager.getLocalizedString(
                    LocalizedString(
                        "Press back again to exit",
                        "اضغط مرة أخرى للخروج"
                    )
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = languageManager.getLocalizedString(
                                LocalizedString(
                                    "Send Money App",
                                    "تطبيق إرسال الأموال"
                                )
                            ),
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF686F89),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFedf1f8)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .imePadding()
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = if (LocalLayoutDirection.current == LayoutDirection.Rtl) Arrangement.Start else Arrangement.End
                ) {
                    LanguageSwitcher(languageManager = languageManager)
                }

                Text(
                    text = languageManager.getLocalizedString(LocalizedString("Service", "الخدمة")),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                ServiceDropdown(
                    services = viewModel.serviceData.services,
                    selectedService = selectedService,
                    onServiceSelected = viewModel::onServiceSelected,
                    languageManager = languageManager
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = languageManager.getLocalizedString(
                        LocalizedString(
                            "Provider",
                            "المزود"
                        )
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                selectedService?.let { service ->
                    ProviderDropdown(
                        providers = service.providers,
                        selectedProvider = selectedProvider,
                        onProviderSelected = viewModel::onProviderSelected,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                selectedProvider?.requiredFields?.forEach { field ->
                    FormField(
                        field = field,
                        value = formData[field.name] ?: "",
                        onValueChange = { value -> viewModel.onValueChange(field, value) },
                        errorMessage = errorMessages[field.name],
                        languageManager = languageManager
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = { viewModel.validateAndSave(onSuccess = onRequestSaved) },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF007BFF),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = languageManager.getLocalizedString(LocalizedString("SEND", "إرسال")),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceDropdown(
    services: List<Service>,
    selectedService: Service?,
    onServiceSelected: (Service) -> Unit,
    languageManager: LanguageManager
) {
    CustomDropdown(
        items = services,
        selectedItem = selectedService,
        onItemSelected = onServiceSelected,
        itemLabel = { languageManager.getLocalizedString(it.label) }
    )
}

@Composable
fun ProviderDropdown(
    providers: List<Provider>,
    selectedProvider: Provider?,
    onProviderSelected: (Provider) -> Unit
) {
    CustomDropdown(
        items = providers,
        selectedItem = selectedProvider,
        onItemSelected = onProviderSelected,
        itemLabel = { it.name }
    )
}

@Composable
fun FormField(
    field: Field,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?,
    languageManager: LanguageManager
) {
    Column {
        Text(
            text = languageManager.getLocalizedString(field.label),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        when (field.type) {
            "text", "msisdn", "number" -> {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorContainerColor = Color.White,
                        errorIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        val placeholderText = when (field.placeholder) {
                            is String -> field.placeholder
                            is Map<*, *> -> {
                                val map = field.placeholder
                                val en = map["en"] as? String ?: ""
                                val ar = map["ar"] as? String ?: ""
                                languageManager.getLocalizedString(LocalizedString(en, ar))
                            }

                            is LocalizedString -> languageManager.getLocalizedString(field.placeholder)
                            else -> ""
                        }
                        Text(placeholderText)
                    },
                    isError = errorMessage?.isNotEmpty() == true,
                    keyboardOptions = if (field.type == "number")
                        KeyboardOptions(keyboardType = KeyboardType.Number)
                    else
                        KeyboardOptions.Default,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            "option" -> {
                CustomDropdown(
                    items = field.options ?: emptyList(),
                    selectedItem = field.options?.firstOrNull { it.name == value },
                    onItemSelected = { selectedOption -> onValueChange(selectedOption.name) },
                    itemLabel = { it.label }
                )
            }
        }

        if (errorMessage?.isNotEmpty() == true) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}