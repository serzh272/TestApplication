package ru.serzh272.testapplication

import androidx.annotation.StringRes
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import ru.serzh272.testapplication.ui.fragments.AuthorizationFragment

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun invalidLoginTest(){
        val mockNavController = mock(NavController::class.java)
        val fragmentScenario = launchFragmentInContainer<AuthorizationFragment>()
        fragmentScenario.onFragment{
            Navigation.setViewNavController(it.requireView(), mockNavController)
        }
        onView(withId(R.id.te_login))
            .perform(typeText("demo"))
        onView(withId(R.id.te_password))
            .perform(typeText("1234"))
        onView(withId(R.id.login_button))
            .perform(click())
        onSnackbar(withText = R.string.authorization_error)
            .check(matches(isDisplayed()))
    }

    fun onSnackbar(@StringRes withText: Int): ViewInteraction {
        return onView(
            CoreMatchers.allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                withText(withText)
            )
        )
    }

    fun onSnackbarButton(@StringRes withText: Int): ViewInteraction {
        return onView(
            allOf(
                withId(com.google.android.material.R.id.snackbar_action),
                withText(withText)
            )
        )
    }

}