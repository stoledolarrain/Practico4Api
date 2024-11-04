package com.example.practico4api.api

import com.example.practico4api.models.Persona
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JSONApiPersonas {

    @GET("/api/personas")
    fun getPersonaList(): Call<List<Persona>>

    @GET("/api/personas/{id}")
    fun getPersonaById(@Path("id") id: Int): Call<Persona>

    @POST("/api/personas")
    fun addPersona(@Body persona: Persona): Call<Void>

    @PUT("/api/personas/{id}")
    fun updatePersona(@Path("id") id: Long, @Body persona: Persona): Call<Void>
}