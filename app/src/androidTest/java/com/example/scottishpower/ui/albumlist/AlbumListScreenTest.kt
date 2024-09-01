package com.example.scottishpower.ui.albumlist

import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.scottishpower.MainActivity
import com.example.scottishpower.util.TestTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AlbumListScreenTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // long timeout due to slow API + chained calls
    private val timeout = 60000

    @Test
    fun whenAppLaunched_andApiReturnsResults_thenUiIsRendered() {
        composeTestRule.waitUntil(60000) { composeTestRule.onNodeWithTag(TestTag.ALBUM_LIST_CONTAINER).isDisplayed() }
        composeTestRule.onNodeWithTag(TestTag.USERNAME_SORT_CHIP).assertExists()
        composeTestRule.onNodeWithTag(TestTag.TITLE_SORT_CHIP).assertExists()
    }

    @Test
    fun whenUserChangedSortType_thenUiIsUpdated() {
        composeTestRule.waitUntil(60000) { composeTestRule.onNodeWithTag(TestTag.ALBUM_LIST_CONTAINER).isDisplayed() }

        // default sort selected
        composeTestRule.onNodeWithTag(TestTag.TITLE_SORT_CHIP).assertIsSelected()
        composeTestRule.onNodeWithTag(TestTag.USERNAME_SORT_CHIP).assertIsNotSelected()

        composeTestRule.onNodeWithTag(TestTag.USERNAME_SORT_CHIP).performClick()

        // username sort selected, title sort deselected
        composeTestRule.onNodeWithTag(TestTag.USERNAME_SORT_CHIP).assertIsSelected()
        composeTestRule.onNodeWithTag(TestTag.TITLE_SORT_CHIP).assertIsNotSelected()

    }



}