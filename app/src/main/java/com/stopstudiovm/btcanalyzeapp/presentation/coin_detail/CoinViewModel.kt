package com.stopstudiovm.btcanalyzeapp.presentation.coin_detail

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stopstudiovm.btcanalyzeapp.common.Constants
import com.stopstudiovm.btcanalyzeapp.common.Resource
import com.stopstudiovm.btcanalyzeapp.domain.use_case.GetCoinUseCase
import com.stopstudiovm.btcanalyzeapp.presentation.coin_detail.components.CoinState
import com.stopstudiovm.btcanalyzeapp.presentation.coin_detail.components.CoinUiState
import com.stopstudiovm.btcanalyzeapp.utils.roundNumber
import com.stopstudiovm.btcanalyzeapp.utils.timeStringTransformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

// Our ViewModel, here we will inject our UseCases that we inject in our constructor
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CoinViewModel @Inject constructor(
    // We need one dependencies
    private val getCoinUseCase: GetCoinUseCase,

) : ViewModel(){
    private val _state = mutableStateOf(CoinState())
    val state: State<CoinState> = _state

    private val _stateCoinUi = mutableStateOf(CoinUiState())
    val stateCoinUi: State<CoinUiState> = _stateCoinUi

    private fun getCoin(startDate: String, endDate: String) {
        getCoinUseCase(startDate,endDate).onEach {
            // on each resource object
            result ->
            when(result){
                // Success
                is Resource.Success -> {
                    _state.value = CoinState(
                        coin = result.data
                    )

                    val dayList = mutableListOf<List<Double>>()
                    var timeFirst = _state.value.coin?.prices?.first()?.get(0)?.toLong()

                    Log.d("coinTimeFirst",timeFirst.toString())

                    // Day list contain only last prices of the day , full list contains hourly values
                    var counter = 0
                    _state.value.coin?.prices?.forEach {
                        counter++
                        val time = it[0].toLong()
                        Log.d("coinTimeLong",time.toString())
                     if (counter / Constants.DAY_HOURS == 1){
                         counter = 0
                         Log.d("coinTimeExtracted",time.toString())
                         timeFirst = time
                         dayList.add(it)
                     }

                    }
                    Log.d("CoinDay",dayList.toString())


                    // Got descending price intervals
                    // TODO: Need to be done more efficiently + calculate intervals and with connected dates
                    var price = Constants.BTC_TOP_VAL
                    val indexes = mutableListOf<Int>()
                    val clearList = mutableListOf<List<Double>>()
                    dayList.forEachIndexed {index,it ->
                        Log.d("coinDAtes",Instant.fromEpochMilliseconds(it[0].toLong()).toString())
                        if (it[1]< price){
                            price = it[1]
                            indexes.add(index)
                            clearList.add(it)
                        }
                    }
                    Log.d("CoinIndexes",indexes.toString())
                    Log.d("CoinClearList",clearList.toString())

                    // Get max volume
                    val maxVolume = _state.value.coin?.volume?.maxByOrNull {
                        coin ->
                        coin[1]
                    }
                    // Get highest price
                    val highestPrice = _state.value.coin?.prices?.maxByOrNull {
                            coin ->
                        coin[1]
                    }
                    // Get lowest price
                    val lowestPrice = _state.value.coin?.prices?.minByOrNull {
                            coin ->
                        coin[1]
                    }
                    // Assign values into the state
                    _stateCoinUi.value = CoinUiState(
                        descendingPrice = clearList,
                        volume = maxVolume!![1].toLong(),
                        timeVol = timeStringTransformation(maxVolume[0].toLong()),
                        priceH = roundNumber(highestPrice!![1],3),
                        timePH = timeStringTransformation(highestPrice[0].toLong()),
                        priceL = roundNumber(lowestPrice!![1],3),
                        timePL = timeStringTransformation(lowestPrice[0].toLong()),
                        )
                }
                // Error
                is Resource.Error -> {
                    _state.value = CoinState(
                        error = result.message?: "An unexpected error occurred")
                    Log.d("coinE",_state.value.coin.toString())

                }
                // Loading
                is Resource.Loading -> {
                    _state.value = CoinState(isLoading = true)
                    Log.d("coinL",_state.value.coin.toString())

                }
            }
        }.launchIn(viewModelScope) // Coroutine start same as launch
    }

    // This is called when object is created - initializer
    //TODO: Need to be done dynamic from user input not hardcoded - time need to be in ms
    init {
        getCoin("1640991600","1641596400")
    }



    @RequiresApi(Build.VERSION_CODES.O)
     fun unixTime(day: String, month: String, year: String): Long{
        Log.d("Test",day)
        val l = LocalDate.parse("$day-$month-$year", DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        return l.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}