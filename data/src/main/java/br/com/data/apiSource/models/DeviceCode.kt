package br.com.data.apiSource.models

data class DeviceCode(
    val device_code: String,
    val expires_in: Int,
    val interval: Int,
    val user_code: String,
    val verification_uri: String
)