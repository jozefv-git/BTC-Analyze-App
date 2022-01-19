package com.stopstudiovm.btcanalyzeapp.domain.repository

import com.stopstudiovm.btcanalyzeapp.data.remote.dto.CoinDto

// Coin interface
interface CoinRepository {
    suspend fun getCoinById(startDate: String, endDate: String): CoinDto
}