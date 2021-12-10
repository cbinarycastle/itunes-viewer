//package com.test.bountifarm.ui
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.test.bountifarm.data.MusicListPagingSourceFactory
//import com.test.bountifarm.data.SearchMusicListRepository
//import com.test.bountifarm.data.TestData
//import com.test.bountifarm.domain.Music
//import com.test.bountifarm.domain.Result
//import com.test.bountifarm.domain.SearchMusicListUseCase
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import org.junit.Test
//
//@ExperimentalCoroutinesApi
//class MusicListViewModelTest {
//
//    @Test
//    fun `검색어가 변경되면 음악 리스트를 불러온다`() = runBlocking {
//        val viewModel = createMusicListViewModel()
//        viewModel.searchMusics("test")
//        viewModel.musics.collect {
//            if (it is Result.Success) {
//                it.data.
//            }
//        }
//    }
//
//    private fun createMusicListViewModel() = MusicListViewModel(createSearchMusicListUseCase())
//
//    private fun createSearchMusicListUseCase(): SearchMusicListUseCase {
//        return SearchMusicListUseCase(
//            dispatcher = TestCoroutineDispatcher(),
//            repository = SearchMusicListRepository(successfulMusicListPagingSourceFactory)
//        )
//    }
//
//    private val successfulMusicListPagingSourceFactory = object : MusicListPagingSourceFactory {
//
//        override fun create(term: String): PagingSource<Int, Music> {
//            return object : PagingSource<Int, Music>() {
//                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Music> {
//                    return LoadResult.Page(TestData.musics, null, 0)
//                }
//
//                override fun getRefreshKey(state: PagingState<Int, Music>): Int? {
//                    return null
//                }
//            }
//        }
//    }
//}