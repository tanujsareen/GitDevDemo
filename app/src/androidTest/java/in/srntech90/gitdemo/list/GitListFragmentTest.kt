package `in`.srntech90.gitdemo.list

import `in`.srntech90.gitdemo.BaseCompactActivity
import `in`.srntech90.gitdemo.R
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Tanuj.Sareen on 27,January,2020
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class GitListFragmentTest {

    @Test
    fun isGitListFragment_Visible() {

        val activityScenario = ActivityScenario.launch(BaseCompactActivity::class.java)

        //First Check Activity is visible
        onView(withId(R.id.coordinatorLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //second check fragment is visible
        onView(withId(R.id.coordinatorLayoutListFrag))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

       /* val abc=MyViewAction().clickChildViewWithId(R.id.cardView)
        val recy:ViewAction? = RecyclerViewActions.actionOnItemAtPosition(
            0,abc

        )

        onView(withId(R.id.recycleViewer)).perform(recy)*/


    }

    class MyViewAction {

        fun clickChildViewWithId(id: Int): ViewAction {

            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController?, view: View) {
                    val v: View = view.findViewById(id)
                    v.performClick()
                }
            }
        }

    }
}