package com.stopstudiovm.btcanalyzeapp.presentation.coin_detail.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stopstudiovm.btcanalyzeapp.presentation.coin_detail.CoinViewModel
import com.stopstudiovm.btcanalyzeapp.utils.DateTextTransformation
import com.stopstudiovm.btcanalyzeapp.utils.roundNumber
import com.stopstudiovm.btcanalyzeapp.utils.timeStringTransformation


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoinScreen(){
    val viewModel: CoinViewModel = hiltViewModel()
    val maxChar = 8
    var startDate by rememberSaveable {
        mutableStateOf("")
    }
    val stateCoinUi = viewModel.stateCoinUi.value
    val state = viewModel.state.value
    //TODO: Add end date input
    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)) {
            // Lets build single item
            item {
                Row (
                    modifier = Modifier.fillMaxWidth(), // So we will take max width but height only how much we required
                ){
                    Text(text = "${1}. ${"Bitcoin"} (${"BTC"}) Analyze",
                        style = MaterialTheme.typography.h2,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Trend Analyze",
                    style = MaterialTheme.typography.h3,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Bear trend ",
                    style = MaterialTheme.typography.h3,
                )
                Spacer(modifier = Modifier.height(16.dp))
                stateCoinUi.descendingPrice?.forEach {
                    item ->
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = timeStringTransformation(item[0].toLong()),
                            style = MaterialTheme.typography.body2,
                            overflow = TextOverflow.Ellipsis // If the text is to long we will just cut it of
                        )
                        Text(
                            text = "${roundNumber(item[1],2)} €",
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Highest trading volume ",
                    style = MaterialTheme.typography.h3,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(5f),
                        text = stateCoinUi.timeVol,
                        style = MaterialTheme.typography.body2,
                        overflow = TextOverflow.Ellipsis // If the text is to long we will just cut it of
                    )
                    Text(
                        text = "With amount",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 4.dp)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "${stateCoinUi.volume.toString()} €",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.body2
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Market opportunities ",
                    style = MaterialTheme.typography.h3,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(5f),
                        text = "Best day for buy",
                        style = MaterialTheme.typography.body2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stateCoinUi.timePL,
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = "${stateCoinUi.priceL} €",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(5f),
                        text = "Best day for sell",
                        style = MaterialTheme.typography.body2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stateCoinUi.timePH,
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = "${stateCoinUi.priceH} €",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Choose range",
                    style = MaterialTheme.typography.h3,
                )
                    Column(modifier = Modifier
                        .fillMaxWidth()) {

                        TextField(
                            singleLine = true,
                            value = startDate,
                            onValueChange = {
                                if (it.length <= maxChar) startDate = it
                            },
                            visualTransformation = DateTextTransformation()
                        )

                        Button(onClick = {
                            //TODO: Input is not working
                        }) {
                            Text(text = "Analyze")
                        }
                    }
            }
        }
        if(state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center) // Align inside center of the box
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CoinDetailScreenPreview(){
    CoinScreen()
}
