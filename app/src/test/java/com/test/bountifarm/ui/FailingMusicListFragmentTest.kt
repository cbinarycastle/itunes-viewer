package com.test.bountifarm.ui

import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.bountifarm.R
import com.test.bountifarm.data.ItunesService
import com.test.bountifarm.data.SearchMusicListResponse
import com.test.bountifarm.di.*
import com.test.bountifarm.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@UninstallModules(NetworkModule::class)
@HiltAndroidTest
class FailingMusicListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var fragment: MusicListFragment
    private lateinit var navController: NavHostController

    @Before
    fun launchScreen() {
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setViewModelStore(ViewModelStore())

        launchFragmentInHiltContainer {
            MusicListFragment().also { fragment ->
                this@FailingMusicListFragmentTest.fragment = fragment
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.nav_graph)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }
    }

    @Test
    fun `음악 리스트 조회 시 오류가 발생했을 경우, 에러 메시지와 재시도 버튼이 노출된다`() {
        onView(withId(R.id.error_message))
            .check(matches(isDisplayed()))
            .check(matches(withText(ERROR_MESSAGE)))

        onView(withId(R.id.retry_button))
            .check(matches(isDisplayed()))
    }

    @InstallIn(SingletonComponent::class)
    @Module
    class FailingNetworkModule {

        @Provides
        fun provideItunesService(): ItunesService {
            return object : ItunesService {
                override suspend fun search(params: Map<String, Any>): SearchMusicListResponse {
                    throw RuntimeException(ERROR_MESSAGE)
                }
            }
        }
    }
}

private const val ERROR_MESSAGE = "Failed"