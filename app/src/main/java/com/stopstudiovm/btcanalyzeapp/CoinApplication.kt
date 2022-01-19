package com.stopstudiovm.btcanalyzeapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// This is only to get Dagger hilt information about the app so dagger Hilt will also have to application context
// If it would need that for the dependencies
@HiltAndroidApp
class CoinApplication : Application()