package com.rungo.runwithzippy.base

import com.rungo.runwithzippy.data.model.ErrorModel

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(errorModel: ErrorModel?)
}

