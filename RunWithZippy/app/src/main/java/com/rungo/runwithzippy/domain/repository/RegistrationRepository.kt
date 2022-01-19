package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.model.RegResponse
import com.rungo.runwithzippy.data.model.RegWithEmail


interface RegistrationRepository {

    suspend fun registrationWithEmail(regWithEmail: RegWithEmail): RegResponse
}