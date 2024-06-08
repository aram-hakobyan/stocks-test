package com.androidart.common.domain.repository

interface TokenRepository {
    fun fetchToken(): String
}