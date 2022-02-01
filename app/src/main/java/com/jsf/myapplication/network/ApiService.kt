package com.jsf.myapplication.network

import com.jsf.myapplication.data.model.Character
import com.jsf.myapplication.data.model.Characters
import com.jsf.myapplication.data.model.Episode
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.logging.Level
import retrofit2.http.Url




private const val BASE_URL = "https://rickandmortyapi.com/"

private val client = OkHttpClient().newBuilder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

// Agregamos un compilador de Retrofit para compilar y crear un objeto Retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .baseUrl(BASE_URL)
    .build()

// Definimos cómo Retrofit se comunica con el servidor web mediante solicitudes HTTP
interface ApiServiceCharacters {
    @GET("api/character")
    suspend fun getCharacters(): Characters // Para llamar a la función desde una corrutina

    @GET("api/character/{id}")
    suspend fun getCharacterDetail(@Path("id") id: Int): Character

    @GET
    suspend fun getEpisodeName(@Url url: String): Episode
}

// Definimos el objeto singleton público al que se puede acceder desde el resto de la app
object Api {
    val apiService: ApiServiceCharacters by lazy {
        retrofit.create(ApiServiceCharacters::class.java)
    } // Operador lambda que toma nuestra propia configuración ¿?
}
// No tiene valor hasta la primera vez que se evalua y luego coserva siempre ese valor

