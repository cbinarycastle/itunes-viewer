package com.test.bountifarm.di

import javax.inject.Qualifier

@Qualifier
annotation class DefaultDispatcher

@Qualifier
annotation class MainDispatcher

@Qualifier
annotation class IoDispatcher