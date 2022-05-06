package com.example.nexam.steps

import com.example.nexam.R
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps
import com.mauriciotogneri.greencoffee.annotations.Then

@Suppress("unused")
class DetailsSteps : GreenCoffeeSteps() {
    @Then("^I see the detail screen for '([\\w| ]+)'$")
    fun `iSeeTheDetailScreenFor$`(username: String?) {
        onViewWithText(username).isDisplayed
    }

    @Then("^I see his name is '([\\w| ]+)'$")
    fun iSeeHisHerNameIs(name: String?) {
        onViewWithId(R.id.exam_name).contains(name)
    }

    @Then("^I see his age is (\\d+)$")
    fun iSeeHisHerAgeIs(age: Int) {
        onViewWithId(R.id.date).contains(age)
    }

    //TODO remove -> only for test
    @Then("^I see his weight is (\\d+\\.?\\d+ kg.)$")
    fun iSeeHisHerWeightIs(weight: String?) {
        onViewWithId(R.id.date).contains(weight)
    }

    @Then("^I see he is (single|married)$")
    fun iSeeHeIsSingleMarried(status: String?) {
        when (status) {
            "single" -> onViewWithId(R.id.date).contains(string(R.string.date))
            "married" -> onViewWithId(R.id.date).contains(string(R.string.date))
            else -> throw RuntimeException()
        }
    }
}