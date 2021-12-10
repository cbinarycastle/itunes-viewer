package com.test.bountifarm.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<P, R>(private val dispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(params: P): Flow<Result<R>> = execute(params)
        .catch { emit(Result.Error(Exception(it))) }
        .flowOn(dispatcher)

    protected abstract suspend fun execute(params: P): Flow<Result<R>>
}