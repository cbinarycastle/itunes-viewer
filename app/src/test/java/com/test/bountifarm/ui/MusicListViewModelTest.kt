package com.test.bountifarm.ui

import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.bountifarm.MainCoroutineRule
import com.test.bountifarm.TestData
import com.test.bountifarm.data.MusicListPagingSourceFactory
import com.test.bountifarm.data.SearchMusicListRepository
import com.test.bountifarm.domain.Music
import com.test.bountifarm.domain.SearchMusicListUseCase
import com.test.bountifarm.runBlockingTest
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MusicListViewModelTest {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MusicListViewModel

    @Before
    fun init() {
        viewModel = createMusicListViewModel()
    }

    @Test
    fun `로딩 상태로 올바르게 변경된다`() = coroutineRule.runBlockingTest {
        // when
        viewModel.onRefreshLoadStateChanged(
            loadState = LoadState.Loading,
            itemCount = 0
        )

        // then
        assertTrue(viewModel.isLoading.first())
        assertFalse(viewModel.isError.first())
    }

    @Test
    fun `에러 상태로 올바르게 변경된다`() = coroutineRule.runBlockingTest {
        // given
        val errorMessage = "error"

        // when
        viewModel.onRefreshLoadStateChanged(
            loadState = LoadState.Error(RuntimeException(errorMessage)),
            itemCount = 0
        )

        // then
        assertEquals(errorMessage, viewModel.errorMessage.first())
        assertTrue(viewModel.isError.first())
        assertFalse(viewModel.isLoading.first())
    }

    @Test
    fun `로드된 데이터가 없을 경우 올바르게 처리된다`() = coroutineRule.runBlockingTest {
        // when
        viewModel.onRefreshLoadStateChanged(
            loadState = LoadState.NotLoading(true),
            itemCount = 0
        )

        // then
        assertTrue(viewModel.isResultEmpty.first())
        assertFalse(viewModel.isLoading.first())
        assertFalse(viewModel.isError.first())
    }

    @Test
    fun `로드된 데이터가 있을 경우 올바르게 처리된다`() = coroutineRule.runBlockingTest {
        // when
        viewModel.onRefreshLoadStateChanged(
            loadState = LoadState.NotLoading(true),
            itemCount = 1
        )

        // then
        assertFalse(viewModel.isResultEmpty.first())
        assertFalse(viewModel.isLoading.first())
        assertFalse(viewModel.isError.first())
    }

    private fun createMusicListViewModel() = MusicListViewModel(createSearchMusicListUseCase())

    private fun createSearchMusicListUseCase(): SearchMusicListUseCase {
        return SearchMusicListUseCase(
            dispatcher = TestCoroutineDispatcher(),
            repository = SearchMusicListRepository(successfulMusicListPagingSourceFactory)
        )
    }

    private val successfulMusicListPagingSourceFactory = object : MusicListPagingSourceFactory {

        override fun create(term: String): PagingSource<Int, Music> {
            return object : PagingSource<Int, Music>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Music> {
                    return LoadResult.Page(TestData.musics, null, 0)
                }

                override fun getRefreshKey(state: PagingState<Int, Music>): Int? {
                    return null
                }
            }
        }
    }

    private val unsuccessfulMusicListPagingSourceFactory = object : MusicListPagingSourceFactory {

        override fun create(term: String): PagingSource<Int, Music> {
            return object : PagingSource<Int, Music>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Music> {
                    return LoadResult.Page(TestData.musics, null, 0)
                }

                override fun getRefreshKey(state: PagingState<Int, Music>): Int? {
                    return null
                }
            }
        }
    }
}