package com.stopstudiovm.btcanalyzeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.LocalContext
import com.stopstudiovm.btcanalyzeapp.presentation.coin_detail.components.CoinScreen
import com.stopstudiovm.btcanalyzeapp.ui.theme.BtcAnalyzeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as AppCompatActivity
            BtcAnalyzeAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CoinScreen(activity)
                }
            }
        }
    }
}
