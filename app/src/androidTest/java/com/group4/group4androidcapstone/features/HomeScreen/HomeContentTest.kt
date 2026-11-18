package com.group4.group4androidcapstone.features.HomeScreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.performClick
import com.group4.group4androidcapstone.features.HomeScreen.presentation.HomeContent
import com.group4.group4androidcapstone.ui.theme.CapstoneTheme
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeContentTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val calls = mutableListOf<String>()

    @Before
    fun setup(){
        //render the composable being tested
        composeRule.setContent{
            CapstoneTheme{
                HomeContent(
                    onNavigateToProductDirectory = {Unit}

                //add more here later

                )
            }
        }
    }


    @Test
    fun givenHomeScreen_whenShown_thenDisplaysTitle(){
        composeRule.onNode(hasTestTag("Home-Title")).assertIsDisplayed()

    }

    @Test
    fun givenHomeScreen_whenShown_thenDisplaysAllTwoButtons(){
        listOf("Recent-Products-Button", "Order-History-Button").forEach{
            composeRule.onNode(hasTestTag(it)).assertIsDisplayed()
        }

    }

//    @Test
//    fun givenDashboard_whenViewArtistsTapped_thenOnViewArtistsIsCalled(){
//        composeRule.onNode(hasTestTag("view-artists-button")).performClick()
//        assertEquals(listOf("artists"),calls)
//    }

//    @Test
//    fun givenDashboard_whenEachButtonIsTapped_thenOnlyItsOwnCallbackIsCalled(){
//        val expectation = mapOf(
//            "view-board-members-button" to "board members",
//            "sign-out-button" to "sign out"
//        )
//
//        expectation.forEach{(label,expectedCall) ->
//            calls.clear()
//            composeRule.onNode(hasTestTag(label)).performClick()
//            assertEquals("tapping $label",listOf(expectedCall), calls)
//        }
//    }

    @Test
    fun givenDashboard_whenNothingTapped_thenNoCallbacksAreCalled(){
        composeRule.waitForIdle()
        assertEquals(emptyList<String>(),calls)
    }

}