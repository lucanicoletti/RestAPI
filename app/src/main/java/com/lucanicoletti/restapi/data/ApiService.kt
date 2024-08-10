package com.lucanicoletti.restapi.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("api/Text/LoremIpsum")
    suspend fun getLoremIpsum(
        @Query("loremType") loremType: String,
        @Query("type") type: String,
        @Query("number") number: Int,
    ): String

    @GET("/api/Text/Password")
    suspend fun generatePassword(
        @Query("length") length: Int,
        @Query("hasDigits") hasDigits: Boolean,
        @Query("hasUpperCase") hasUpperCase: Boolean,
        @Query("hasSpecials") hasSpecials: Boolean,
    ): String

    @POST("/api/Text/Humanize")
    suspend fun humanizeText(
        @Body humanizeRequestBody: HumanizeRequestBody
    ): String

}