package com.stopstudiovm.btcanalyzeapp.domain.use_case

import com.stopstudiovm.btcanalyzeapp.common.Resource
import com.stopstudiovm.btcanalyzeapp.data.remote.dto.toCoinModel
import com.stopstudiovm.btcanalyzeapp.data.repository.model.CoinModel
import com.stopstudiovm.btcanalyzeapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

// We need to inject our repository here
class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository // We want to inject interface because that's allow us to be easily replaceable
){
    // Executor
    // We override the operator fun invoke
    // We override invoke function which basically means that we can call our UseCase as it was a function
    // With calling coroutines flow
    // If we will have data then it would be of type List<Coins>...so if it was successful we will just attach list of coins
    operator fun invoke(startDate: String, endDate: String): Flow<Resource<CoinModel>> = flow {
        try {
            emit(Resource.Loading<CoinModel>()) // So in our UI we can display our progressBar
            val coin = repository.getCoinById(startDate,endDate).toCoinModel() // So this is our UI Object again
            emit(Resource.Success<CoinModel>(coin)) // Here will be data what we want to forward to our viewModel that contains our UseCase
        } catch (e: HttpException){ // So if Response start with different number than 2..
            emit(Resource.Error<CoinModel>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException){ // If our API or repository even cannot communicate to remote API..no internet or whatever
            emit(Resource.Error<CoinModel>(e.localizedMessage ?: "Couldn't reach the server. Check your internet connection."))
        }
    }
}