package com.lucanicoletti.restapi.data

import retrofit2.http.GET

interface ApiService {

    @GET("/facts/random")
    suspend fun getRandomFact(): FactEntity

    @GET("/facts/random?quantity=2")
    suspend fun getRandomFacts(): FactEntity

}