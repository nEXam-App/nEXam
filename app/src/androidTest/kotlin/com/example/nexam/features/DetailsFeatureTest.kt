package com.example.nexam.features

import androidx.test.rule.ActivityTestRule
import com.example.nexam.TestSuite
import com.example.nexam.MainActivity
import com.example.nexam.steps.ContactListSteps
import com.example.nexam.steps.DetailsSteps
import com.example.nexam.steps.ScreenshotSteps
import com.mauriciotogneri.greencoffee.GreenCoffeeConfig
import com.mauriciotogneri.greencoffee.GreenCoffeeTest
import com.mauriciotogneri.greencoffee.ScenarioConfig
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.IOException

@RunWith(Parameterized::class)
class DetailsFeatureTest(scenarioConfig: ScenarioConfig?) : GreenCoffeeTest(scenarioConfig) {

    @Rule @JvmField
    var activity = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test() {
        start(ContactListSteps(),
            DetailsSteps(),
            ScreenshotSteps())
    }

    companion object {
        @Parameterized.Parameters(name = "{0}")
        @Throws(IOException::class)

        @JvmStatic
        fun scenarios(): Iterable<ScenarioConfig> {
            return GreenCoffeeConfig()
                .withFeatureFromAssets("assets/details.feature")
                .takeScreenshotOnFail()
                .scenarios(TestSuite.ENGLISH, TestSuite.SPANISH)
        }
    }
}