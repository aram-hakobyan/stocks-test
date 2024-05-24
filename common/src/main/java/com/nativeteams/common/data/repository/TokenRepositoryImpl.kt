package com.nativeteams.common.data.repository

import com.nativeteams.common.domain.repository.TokenRepository

class TokenRepositoryImpl : TokenRepository {

    // todo: Fetch from the server and store using Encrypted SharedPreferences
    override fun loadToken() = "2d591e50efmshc336a60ad9db646p107f36jsne14b521bce0c"

}