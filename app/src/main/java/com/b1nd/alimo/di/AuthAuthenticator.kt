package com.b1nd.alimo.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DAuthHttpClient
