package com.test.bountifarm.ui

import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.bountifarm.R
import com.test.bountifarm.TestData
import com.test.bountifarm.data.FakeItunesService
import com.test.bountifarm.data.ItunesService
import com.test.bountifarm.di.*
import com.test.bountifarm.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@UninstallModules(NetworkModule::class)
@HiltAndroidTest
class MusicListFragmentTest {

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
                this@MusicListFragmentTest.fragment = fragment
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
    fun `음악 리스트를 표시한다`() {
        onView(withId(R.id.recycler_view)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val sizeOfItemAndEndOfPaginationView = TestData.musicData.size + 1
            assertEquals(
                sizeOfItemAndEndOfPaginationView,
                (view as RecyclerView).adapter?.itemCount
            )
        }
    }

    @InstallIn(SingletonComponent::class)
    @Module
    class TestNetworkModule {

        @Provides
        fun provideItunesService(): ItunesService {
            return FakeItunesService().apply {
                TestData.musicData.forEach { addMusic(it) }
            }
        }
    }
}