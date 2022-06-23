package com.example.nexam.features

import com.mauriciotogneri.greencoffee.GreenCoffeeTest
import com.mauriciotogneri.greencoffee.ScenarioConfig

//@RunWith(Parameterized::class)
class DetailsFeatureTest(scenarioConfig: ScenarioConfig?) : GreenCoffeeTest(scenarioConfig) {

    //TODO REMOVE IF NOT NEEDED ANYMORE

  /*  @Rule @JvmField
    var activity = ActivityTestRule(com.example.nexam.MainActivity::class.java)

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
    }*/
}