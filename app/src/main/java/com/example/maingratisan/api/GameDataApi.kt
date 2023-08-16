package com.example.maingratisan.api

import com.example.maingratisan.model.GameData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GameDataApi {
    @GET("giveaways")
    suspend fun getGames(): List<GameData>

    companion object {
        private const val BASE_URL = "https://www.gamerpower.com/api/"

        private var gameDataApi: GameDataApi? = null
        fun getInstance(): GameDataApi {
            if (gameDataApi == null) {
                gameDataApi = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GameDataApi::class.java)
            }

            return gameDataApi!!
        }
    }
}