package com.test.bountifarm.ui

import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.bountifarm.MainCoroutineRule
import com.test.bountifarm.R
import com.test.bountifarm.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SearchFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var coroutineRule = MainCoroutineRule()

    private lateinit var navController: NavHostController

    @Before
    fun launchScreen() {
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setViewModelStore(ViewModelStore())
        navController.setGraph(R.navigation.nav_graph)

        // Add MusicListFragment to BackStack
        navController.navigate(MusicListFragmentDirections.toSearch())

        launchFragmentInHiltContainer {
            SearchFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }
    }

    @Test
    fun `노래 제목 검색 시 검색어 데이터를 담아 이전 화면으로 이동한다`() {
        // given
        val query = "query"

        // when
        onView(withId(R.id.search_src_text))    // TextView ID in SearchView
            .perform(typeText(query))
            .perform(pressImeActionButton())

        // then
        assertEquals(
            query,
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.get<String>(SearchFragment.RESULT_KEY_QUERY)
        )
        assertEquals(
            R.id.musicListFragment,
            navController.currentDestination?.id
        )
    }
}