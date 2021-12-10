package com.test.bountifarm.domain

import androidx.paging.PagingData
import com.test.bountifarm.data.SearchMusicListRepository
import com.test.bountifarm.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchMusicListUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: SearchMusicListRepository,
) : FlowUseCase<String, PagingData<Music>>(dispatcher) {

    override suspend fun execute(params: String): Flow<Result<PagingData<Music>>> {
        return repository.getSearchResultStream(params)
            .map { Result.Success(it) }
    }
}