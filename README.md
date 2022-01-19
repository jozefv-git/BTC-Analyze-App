# BTC-Analyze-App

BTC-Analyze-App is basic Android Native Application with Clean Architecture and MVVM.

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
- Range of values for the API is hardcoded and input field is not working.
- For change of the range from which Retrofit is fetching data, use function getCoin(startDate,endDate) inside the CoinViewModel.
