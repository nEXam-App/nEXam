package com.example.nexam.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.nexam.R
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps
import com.mauriciotogneri.greencoffee.annotations.Given
import com.mauriciotogneri.greencoffee.annotations.Then
import com.mauriciotogneri.greencoffee.annotations.When

@Suppress("unused")
class ShowDashboardSteps : GreenCoffeeSteps() {
    @Given("^I am on the dashboard screen$")
    fun Dashboardscreen() {
        onViewWithText("nEXam").isDisplayed
    }

    @When("^I select the exam called \'(.+)\'$")
    fun `iSelectTheExamCalled$`(examname: String) {
        onView(ViewMatchers.withText(examname)).perform(ViewActions.click())
    }

    @Then("^I see the exam screen$")
    fun iSeeTheContactsScreen() {
        onViewWithText(R.string.contacts_title).isDisplayed
    }
}