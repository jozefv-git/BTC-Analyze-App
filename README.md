# BTC-Analyze-App

BTC-Analyze-App is basic Android Native Application with Clean Architecture and MVVM + usage of Jetpack Compose.

<img src="https://i.ibb.co/RjwwF9R/Screenshot-20220124-125842-Btc-Analyze-App.jpg" width="300" height="600">
<img src="https://i.ibb.co/SVvX8t7/Screenshot-20220124-125915-Btc-Analyze-App.jpg" width="300" height="600">


- Currently working branch - Jozef
- Main Branch for now contains only README.md
 ### Working functionality
 - Request from the API based on the range selection + update selection if range is bigger than 90 days (API requirements).
 - Bear trend is showing all decreasing intervals in selected range - if price is lower than the price from day before, bear trend will continue, if price is higher trend is interrupted. Results are based on Daily closing price.
 - Highest trading volume is calculated for selected range with help of MaxByOrNull function based on the daily close.
 - Best day for buy / sell are shown with help of  MaxByOrNull / MinByOrNull function, selection is made from all recieved hourly results for selected range. User will see not exact hour, but lower / highest price from seleced day. At the moment, sell price is based on the highest value for the selected range, if the highest value occured before buy opportunity, then user will see - "No suitable time for sell".


## Features



- Jetpack Composable
- API connection with Coingecko
- Range selection with decreasing trend analyze
- Highest trading volume
- Market opportunities

## Used
- Kotlin
- CoinGecko API
- Retrofit
- Dagger Hilt
- Coroutines

## Support Links
- https://www.coingecko.com/en/api
- https://square.github.io/retrofit/
- https://dagger.dev/hilt/




## Dependencies
Conversion of unix time

```bash
  implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
```

Retrofit

```bash
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation "com.squareup.okhttp3:okhttp:5.0.0-alpha.2"
    implementation "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2"
```

Coroutines

```bash
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2'
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
```
Dagger Hilt

```bash
    implementation "com.google.dagger:hilt-android:2.39.1"
    kapt "com.google.dagger:hilt-compiler:2.39.1"
    implementation "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
    kapt "androidx.hilt:hilt-compiler:1.0.0"
    implementation 'androidx.hilt:hilt-navigation-compose:1.0.0-rc01'
```

Plugins

```bash
plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin'
}
```

## Notes
Aplication is in developing process. I am not a proffesional, I try to learn new techniques.
### Todo:
- [x] Range of values for the API is hardcoded and input field is not working. - Solved with remove of Edit texts and added DatePicker.
- [ ] Find longest continuous decreasing interval, at the moment we are getting all decreasing intervals.
- [ ] Prices for Sell and Buy are based on the maximum and minimum value from the list - If the Highest price from the range was before buy price - then the user cannot see anything - Need to be fixed (prob. need to find highest value before start of another decreasing).
