package com.enablex.uikitsample.utility
import android.content.Context
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object JsonParser {
    fun getParams(): JsonObject {
        val jsonObject = JsonObject()
        try {
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonObject
    }
    fun getTokenParams(roomId: String, name: String, role: String): JsonObject {
        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("name", name)
            jsonObject.addProperty("role", role)
            jsonObject.addProperty("user_ref", "2236")
            jsonObject.addProperty("roomId", roomId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonObject
    }
    fun getRoomId(success: String): String {
        var id: String=""
        try {
            val jsonObject = JSONObject(success)
            id = jsonObject.getJSONObject("room").optString("room_id")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return id
    }
    fun getRole(success: String): String {
        var role: String=""
        try {
            val jsonObject = JSONObject(success)
            role = jsonObject.getString("role")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return role
    }
    fun getToken(success: String): String {
        var token: String = ""
        try {
            val jsonObject = JSONObject(success)
            token = jsonObject.getString("token")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return token
    }
}
