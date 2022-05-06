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
class ContactListSteps : GreenCoffeeSteps() {
    @Given("^I am on the contacts screen$")
    fun Contactscreen() {
        onViewWithText("People").isDisplayed
    }

    @When("^I select the contact called \'(.+)\'$")
    fun `iSelectTheContactCalled$`(username: String) {
        onView(ViewMatchers.withText(username)).perform(ViewActions.click())
    }

    @Then("^I see an empty contact list$")
    fun iSeeAnEmptyContactList() {
        onViewWithText(R.string.contacts_emptyList).isDisplayed
    }

    @Then("^I see the contacts screen$")
    fun iSeeTheContactsScreen() {
        onViewWithText(R.string.contacts_title).isDisplayed
    }
}