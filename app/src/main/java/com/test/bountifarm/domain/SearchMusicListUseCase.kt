package com.test.bountifarm.domain

import com.test.bountifarm.data.SearchMusicListRepository
import com.test.bountifarm.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchMusicListUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: SearchMusicListRepository,
) : UseCase<String, List<Music>>(dispatcher) {

    override suspend fun execute(params: String): List<Music> {
        return repository.search(params)
    }
}