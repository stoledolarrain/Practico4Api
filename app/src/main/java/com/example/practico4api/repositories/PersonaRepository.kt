package com.example.practico4api.repositories

import com.example.practico4api.api.JSONApiPersonas
import com.example.practico4api.models.Persona
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PersonaRepository {

    private val api: JSONApiPersonas

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apicontactos.jmacboy.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(JSONApiPersonas::class.java)
    }

    fun getPersonas(onResult: (List<Persona>) -> Unit) {
        api.getPersonaList().enqueue(object : Callback<List<Persona>> {
            override fun onResponse(call: Call<List<Persona>>, response: Response<List<Persona>>) {
                if (response.isSuccessful) {
                    onResult(response.body() ?: emptyList())
                } else {
                    onResult(emptyList())
                }
            }

            override fun onFailure(call: Call<List<Persona>>, t: Throwable) {
                onResult(emptyList())
            }
        })
    }

    fun getPersonaById(id: Int, onSuccess: (Persona) -> Unit, onError: (Throwable) -> Unit) {
        api.getPersonaById(id).enqueue(object : Callback<Persona> {
            override fun onResponse(call: Call<Persona>, response: Response<Persona>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(Throwable("Error en la respuesta"))
                }
            }

            override fun onFailure(call: Call<Persona>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun updatePersona(persona: Persona, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        api.updatePersona(persona.id, persona).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(Throwable("Error al actualizar el contacto"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }
    // Método para agregar un nuevo contacto
    fun addPersona(persona: Persona, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        api.addPersona(persona).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(Throwable("Error al agregar el contacto"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }
}
