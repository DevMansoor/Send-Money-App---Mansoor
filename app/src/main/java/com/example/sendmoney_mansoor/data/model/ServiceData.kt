package com.example.sendmoney_mansoor.data.model

import com.google.gson.annotations.SerializedName


data class ServiceData(
    @SerializedName("title") val title: LocalizedString,
    @SerializedName("services") val services: List<Service>
)

data class LocalizedString(
    @SerializedName("en") val en: String,
    @SerializedName("ar") val ar: String
)

data class Service(
    @SerializedName("label") val label: LocalizedString,
    @SerializedName("name") val name: String,
    @SerializedName("providers") val providers: List<Provider>
)

data class Provider(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: String,
    @SerializedName("required_fields") val requiredFields: List<Field>
)

data class Field(
    @SerializedName("label") val label: LocalizedString,
    @SerializedName("name") val name: String,
    @SerializedName("placeholder") val placeholder: Any?,
    @SerializedName("type") val type: String,
    @SerializedName("validation") val validation: String,
    @SerializedName("max_length") val maxLength: Any,
    @SerializedName("validation_error_message") val validationErrorMessage: Any,
    @SerializedName("options") val options: List<Option>? = null
)

data class Option(
    @SerializedName("label") val label: String,
    @SerializedName("name") val name: String
)