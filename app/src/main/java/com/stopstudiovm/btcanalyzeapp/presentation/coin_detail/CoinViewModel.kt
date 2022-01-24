package com.stopstudiovm.btcanalyzeapp.presentation.coin_detail

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.stopstudiovm.btcanalyzeapp.common.Constants
import com.stopstudiovm.btcanalyzeapp.common.Resource
import com.stopstudiovm.btcanalyzeapp.domain.use_case.GetCoinUseCase
import com.stopstudiovm.btcanalyzeapp.presentation.coin_detail.components.CoinState
import com.stopstudiovm.btcanalyzeapp.presentation.coin_detail.components.CoinUiState
import com.stopstudiovm.btcanalyzeapp.utils.DateTextTransformation
import com.stopstudiovm.btcanalyzeapp.utils.roundNumber
import com.stopstudiovm.btcanalyzeapp.utils.timeStringTransformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

// Our ViewModel, here we will inject our UseCases that we inject in our constructor
@HiltViewModel
class CoinViewModel @Inject constructor(
    // We need one dependencies
    private val getCoinUseCase: GetCoinUseCase,

) : ViewModel(){
    private val _state = mutableStateOf(CoinState())
    val state: State<CoinState> = _state

    private val _stateCoinUi = mutableStateOf(CoinUiState())
    val stateCoinUi: State<CoinUiState> = _stateCoinUi

    private var startDateS: Long? = null
    private var endDateS: Long? = null


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
                    val coin = _state.value.coin

                    //Testing: Log.d("complete",coin?.prices.toString())
                    val dayList = mutableListOf<List<Double>>()
                    var timeFirst = coin?.prices?.first()?.get(0)?.toLong()

                    // !!IMPORTANT!!
                    // Day list contain only last (closing) prices of the day
                    var counter = 0
                    _state.value.coin?.prices?.forEach {
                        counter++
                        val time = it[0].toLong()
                        //Testing:  Log.d("coinTimeLong",time.toString())
                        // API returns hourly interval per day if range is between 1 - 90 days, more than 90,
                        // it returns daily close
                     if((startDateS!! + Constants.DAY_SEC*90) < endDateS!!){
                         //Testing:  Log.d("day90","More than 90")
                         dayList.add(it)
                     } else{
                         // Divide 24 hourly records per day, so we will get last closing daily value
                         if (counter / Constants.DAY_HOURS == 1){
                             counter = 0
                             //Testing: Log.d("coinTimeExtracted",time.toString())
                             timeFirst = time
                             dayList.add(it)
                         }
                         //Testing: Log.d("day90","Less than 90")
                     }
                    }
                    //Testing: Log.d("CoinDay",dayList.toString())


                    // Descending price intervals
                    // TODO: Find longest continuous decreasing interval, at the moment we are getting all decreasing intervals
                    var priceDefault = Constants.BTC_TOP_VAL
                    //Testing: val indexes = mutableListOf<Int>()
                    val clearList = mutableListOf<List<Double>>()
                    dayList.forEachIndexed {index,it ->
                        //Testing: val time = it[0]
                        val price = it[1]
                        //Testing:  Log.d("coinDates",Instant.fromEpochMilliseconds(time.toLong()).toString())
                        if (price < priceDefault){
                            priceDefault = price
                            //Testing: indexes.add(index)
                            clearList.add(it)
                        }
                    }
                    //Testing:  Log.d("CoinIndexes",indexes.toString())
                    //Testing: Log.d("CoinClearList",clearList.toString())


                    // Get max volume
                    val maxVolumeObj = coin?.volume?.maxByOrNull {
                        obj ->
                        obj[1]
                    }
                    val maxVolume = maxVolumeObj!![1]
                    val maxVolumeTime = maxVolumeObj[0]


                   //Testing: val price = coin?.prices
                   //Testing: Log.d("prices",price.toString())

                    // Get highest price - from all range records
                    // Sort based on the price - return time and price
                    val highestPriceObj = coin.prices.maxByOrNull {
                            obj ->
                        obj[1]
                    }
                    val highestPrice = highestPriceObj!![1]
                    val highestPriceTime = highestPriceObj[0]

                    //Testing: Log.d("pricesH",highestPrice.toString())
                    //Testing: Log.d("pricesT", timeStringTransformation(highestPriceTime.toLong()))

                    // Get lowest price from all range records
                    val lowestPriceObj = coin.prices.minByOrNull {
                            obj ->
                        obj[1]
                    }
                    val lowestPrice = lowestPriceObj!![1]
                    val lowestPriceTime = lowestPriceObj[0]

                    // If max price is after buy date, we can show it
                    if(highestPriceTime > lowestPriceTime){
                        // !!IMPORTANT!!
                        // Assign values into the state - values are based on Hourly time frame, not daily close
                        _stateCoinUi.value = CoinUiState(
                            descendingPrice = clearList,
                            volume = maxVolume.toLong(),
                            timeVol = timeStringTransformation(maxVolumeTime.toLong()),
                            priceH = roundNumber(highestPrice,3),
                            timePH = timeStringTransformation(highestPriceTime.toLong()),
                            priceL = roundNumber(lowestPrice,3),
                            timePL = timeStringTransformation(lowestPriceTime.toLong()),
                        )
                    } else{
                        _stateCoinUi.value = CoinUiState(
                            descendingPrice = clearList,
                            volume = maxVolume.toLong(),
                            timeVol = timeStringTransformation(maxVolumeTime.toLong()),
                            priceH = null,
                            timePH = "--No suitable time for sell--",
                            priceL = roundNumber(lowestPrice,3),
                            timePL = timeStringTransformation(lowestPriceTime.toLong()),
                        )
                    }


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
        }.launchIn(viewModelScope) // Coroutine start background
    }


    fun showDatePicker(activity: AppCompatActivity){

        val startYear = yearToMillis("UTC",Constants.PICKER_MIN_START_YEAR,
            Constants.PICKER_MIN_START_MONTH,
            Constants.PICKER_MIN_START_DAY)

        val calConstraints = setPickerConstraints(startYear)

        val picker = MaterialDatePicker.Builder.dateRangePicker()
            .setCalendarConstraints(calConstraints.build()).build()
        activity.let {
            picker.show(it.supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                    date ->
                val startDate = (date.first / Constants.SEC_TO_MILLIS)
                val endDate = (date.second / Constants.SEC_TO_MILLIS)
                Log.d("timeStartRange",date.first.toString())
                Log.d("timeEndRange",date.second.toString())
                getCoin(startDate.toString(),endDate.toString())
                startDateS = startDate
                endDateS = endDate
            }
        }
    }


    // Conversion of selected date into the millis
    private fun yearToMillis(timezone: String,year: Int, month: Int, day: Int): Long{
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone))
        calendar.set(year, month, day)
        return calendar.timeInMillis
    }

    // Constrains for datePicker
    private fun setPickerConstraints(startYear: Long): CalendarConstraints.Builder{
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        return CalendarConstraints.Builder()
            .setStart(startYear)
            .setEnd(today)
            .setValidator(DateValidatorPointBackward.now())
    }
}