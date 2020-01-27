package `in`.srntech90.gitdemo

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Tanuj.Sareen on 27,January,2020
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class BaseCompactActivityTest {


    /*test case for testing is Base Activity visible*/
    @Test
    fun isActivity_Displayed() {

        val activitySenerio = ActivityScenario.launch(BaseCompactActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.coordinatorLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}