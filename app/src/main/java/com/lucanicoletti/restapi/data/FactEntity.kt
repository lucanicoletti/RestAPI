package com.lucanicoletti.restapi.data

import com.google.gson.annotations.SerializedName

data class FactEntity(
    @SerializedName("_id") val id: String,
    @SerializedName("__v") val version: Long,
    @SerializedName("text") val text: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("deleted") val deleted: Boolean,
    @SerializedName("source") val source: String,
    @SerializedName("sentCount") val sentCount: Long,
)