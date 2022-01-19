package com.stopstudiovm.btcanalyzeapp.data

import com.stopstudiovm.btcanalyzeapp.data.remote.dto.CoinDto
import com.stopstudiovm.btcanalyzeapp.domain.repository.CoinRepository
import javax.inject.Inject

// Inject dependencies here
class CoinRepositoryImplementation @Inject constructor(

    //Our API instance
    private val api: CoinGeckoApi

) : CoinRepository { // Now we need to implement our repository


    override suspend fun getCoinById(startDate: String, endDate: String): CoinDto {
        return api.getCoinById(startDate,endDate)
    }
}