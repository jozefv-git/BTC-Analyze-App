package com.stopstudiovm.btcanalyzeapp.presentation.coin_detail.components

import com.stopstudiovm.btcanalyzeapp.data.repository.model.CoinModel

data class CoinState(
    val isLoading: Boolean = false,
    val coin: CoinModel? = null, // Default it will be null
    val error: String = ""

)
