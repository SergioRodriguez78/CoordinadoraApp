package com.coordinadora.coordinadoraapp.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
sealed interface CoordinadoraRoutes {

    @Serializable
    data object Splash : CoordinadoraRoutes

    @Serializable
    data object Login : CoordinadoraRoutes

    @Serializable
    data object Home : CoordinadoraRoutes

    @Serializable
    data object Map : CoordinadoraRoutes

}
