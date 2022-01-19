package com.stopstudiovm.btcanalyzeapp.data

import com.stopstudiovm.btcanalyzeapp.data.remote.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// API with retrofit
interface CoinGeckoApi {

    @GET("market_chart/range?vs_currency=eur") // This is how we define path url parameter
    suspend fun getCoinById(@Query("from") startDate: String,
                            @Query("to") endDate: String): CoinDto
}