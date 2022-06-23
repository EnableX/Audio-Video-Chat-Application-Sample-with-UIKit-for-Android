package com.enablex.uikitsample.network
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiClient {
    @POST("createRoom")
    suspend fun createRoom(@Body body: JsonObject?): JsonObject

    @POST("createToken")
    suspend fun createToken(@Body body: JsonObject): JsonObject

    @POST("getRoom")
    suspend fun getRoom(@Body body: JsonObject): JsonObject

}
