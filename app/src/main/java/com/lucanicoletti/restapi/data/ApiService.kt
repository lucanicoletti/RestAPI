package com.lucanicoletti.restapi.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/facts/random")
    suspend fun getRandomFact(): FactEntity

    @GET("/facts/random")
    suspend fun getRandomFacts(
        @Query("amount") quantity: Int = 5,
        @Query("animal_type") animalType: String = "cat"
    ): List<FactEntity>

    @GET(value = "/facts/{id}")
    suspend fun getSingleFact(
        @Path(value = "id") byId: String
    ): FactEntity

}