package com.nativeteams.common.domain.repository

interface TokenRepository {
    fun fetchToken(): String
}