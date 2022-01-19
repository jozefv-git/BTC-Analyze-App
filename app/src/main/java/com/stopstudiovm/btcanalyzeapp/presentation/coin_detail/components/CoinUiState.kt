package com.stopstudiovm.btcanalyzeapp.presentation.coin_detail.components

data class CoinUiState(
    val descendingPrice: List<List<Double>>? = null,
    val timeVol: String = "",
    val volume: Long? = null,
    val timePH: String = "",
    val priceH: Double? = null,
    val timePL: String = "",
    val priceL: Double? = null
)
