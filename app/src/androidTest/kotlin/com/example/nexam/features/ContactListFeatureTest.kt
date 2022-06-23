package com.example.nexam.features

import com.mauriciotogneri.greencoffee.GreenCoffeeTest
import com.mauriciotogneri.greencoffee.ScenarioConfig

//@RunWith(Parameterized::class)
class ContactListFeatureTest(scenarioConfig: ScenarioConfig?) : GreenCoffeeTest(scenarioConfig) {

    //TODO REMOVE IF NOT NEEDED ANYMORE

   /* @Rule @JvmField
    val activity = ActivityTestRule(com.example.nexam.MainActivity::class.java, true, true)

    @Test
    fun test() {
        start(
            ContactListSteps(),
            DetailsSteps(),
            ScreenshotSteps())
    }

    companion object {
        @Parameterized.Parameters(name = "{0}")

        @JvmStatic
        fun scenarios(): Iterable<ScenarioConfig> {
            return GreenCoffeeConfig()
                .withFeatureFromAssets("assets/contacts.feature")
                .takeScreenshotOnFail()
                .scenarios(TestSuite.ENGLISH, TestSuite.SPANISH)
        }
    }*/
}

