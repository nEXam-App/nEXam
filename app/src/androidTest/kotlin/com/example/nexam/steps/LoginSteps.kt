package com.example.nexam.steps

import com.example.nexam.R
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps
import com.mauriciotogneri.greencoffee.annotations.Given
import com.mauriciotogneri.greencoffee.annotations.Then
import com.mauriciotogneri.greencoffee.annotations.When

class LoginSteps : GreenCoffeeSteps() {
    //TODO REMOVE
    @Given("^I see an empty login form$")
    fun iSeeAnEmptyLoginForm() {
        onViewWithId(R.id.date).isEmpty
        onViewWithId(R.id.date).isEmpty
    }

    @Given("^I login as (\\w+)$")
    fun `iLoginAs$`(user: String?) {
        onViewWithId(R.id.date).click()
    }

    @When("^I press the login button$")
    fun iPressTheLoginButton() {
        onViewWithId(R.id.date).click()
    }

    @When("^I introduce a valid username$")
    fun iIntroduceAValidUsername() {
        onViewWithId(R.id.date).type("")
    }

    @When("^I introduce a valid password$")
    fun iIntroduceAValidPassword() {
        onViewWithId(R.id.date).type("UserDatabase.USER_1.password()")
    }

    @When("^I introduce an invalid username$")
    fun iIntroduceAnInvalidUsername() {
        onViewWithId(R.id.date).type(INVALID_USERNAME)
    }

    @When("^I introduce an invalid password$")
    fun iIntroduceAnInvalidPassword() {
        onViewWithId(R.id.date).type(INVALID_PASSWORD)
    }

    @When("^I introduce as username (.+)$")
    fun iIntroduceAsUsername(username: String?) {
        onViewWithId(R.id.date).type(username)
    }

    @When("^I introduce as password (.+)$")
    fun iIntroduceAsPassword(password: String?) {
        onViewWithId(R.id.date).type(password)
    }

    @Then("^I see an error message saying 'Invalid username'$")
    fun iSeeAnErrorMessageSayingInvalidUsername() {
        onViewWithText(R.string.date).isDisplayed
    }

    @Then("^I see an error message saying 'Invalid password'$")
    fun iSeeAnErrorMessageSayingInvalidPassword() {
        onViewWithText(R.string.date).isDisplayed
    }

    @Then("^I see an error message saying 'Invalid credentials'$")
    fun iSeeAnErrorMessageSayingInvalidCredentials() {
        onViewWithText(R.string.date).isDisplayed
    }

    companion object {
        private const val INVALID_USERNAME = "guest"
        private const val INVALID_PASSWORD = "5678"
    }
}