package com.coordinadora.coordinadoraapp.firebase

interface FirebaseManager {
    fun createRemoteUser( validationPeriod: Int)
    fun deleteRemoteUser()
    fun decreaseValidationPeriod()
}
