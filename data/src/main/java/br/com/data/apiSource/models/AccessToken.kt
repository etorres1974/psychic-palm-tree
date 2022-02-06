package br.com.data.apiSource.models

data class AccessToken(
    val access_token: String,
    val scope: String,
    val token_type: String
)