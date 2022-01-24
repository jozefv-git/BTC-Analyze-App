package com.stopstudiovm.btcanalyzeapp.data.remote.dto

import com.stopstudiovm.btcanalyzeapp.data.repository.model.CoinModel

// Data transfer object -> which we are getting from the API

data class CoinDto(
    val market_caps: List<List<Double>>,
    val prices: List<List<Double>>,
    val total_volumes: List<List<Double>>
)

// Conversion of the model which we got from Json response to our basic CoinModel which will be used inside our UI

fun CoinDto.toCoinModel(): CoinModel{
    return CoinModel(
        prices = prices,
        volume = total_volumes,
        time = market_caps
    )
}