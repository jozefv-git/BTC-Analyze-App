package com.stopstudiovm.btcanalyzeapp.data.repository.model

data class CoinModel(
    val prices: List<List<Double>>,
    val time: List<List<Double>>,
    val volume: List<List<Double>>,
)
