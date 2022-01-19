package com.stopstudiovm.btcanalyzeapp.di

import com.stopstudiovm.btcanalyzeapp.common.Constants
import com.stopstudiovm.btcanalyzeapp.data.CoinGeckoApi
import com.stopstudiovm.btcanalyzeapp.data.CoinRepositoryImplementation
import com.stopstudiovm.btcanalyzeapp.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Dagger Hilt dependency injection

@Module // All of the dependencies in the module live as long as dependencies does
@InstallIn(SingletonComponent::class) // This will only determine how long will this component live - so this will live as long as our app
object AppModule {
    // Functions that create our dependencies
    @Provides
    @Singleton // This means there is only one instance during our whole life time of the app
    fun provideCoinGeckoApi(): CoinGeckoApi {
        // We are telling dagger hilt like to get coinPaprikaApi
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson to serialize / deserialize the data
            .build()
            .create(CoinGeckoApi::class.java) // Now we define API interface what we want to create here
    }

    // This will provide our repository
    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinGeckoApi): CoinRepository {
        return CoinRepositoryImplementation(api)
    }
}