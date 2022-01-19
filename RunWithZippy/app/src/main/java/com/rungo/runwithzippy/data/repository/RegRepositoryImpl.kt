package com.rungo.runwithzippy.data.repository

import com.rungo.runwithzippy.api.ApiService
import com.rungo.runwithzippy.data.model.RegResponse
import com.rungo.runwithzippy.data.model.RegWithEmail
import com.rungo.runwithzippy.domain.repository.RegistrationRepository

class RegRepositoryImpl(private val apiService: ApiService) : RegistrationRepository {

    override suspend fun registrationWithEmail(regWithEmail: RegWithEmail): RegResponse {
        return apiService.registrationWithEmail(regWithEmail)
    }

}