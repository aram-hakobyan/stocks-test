package com.nativeteams.common.data.repository

import com.nativeteams.common.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor() : TokenRepository {

    // todo: Fetch from the server and store using Encrypted SharedPreferences
    override fun fetchToken() = "2d591e50efmshc336a60ad9db646p107f36jsne14b521bce0c"
}