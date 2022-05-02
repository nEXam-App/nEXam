package com.example.nexam.steps;

import com.example.nexam.R;
import com.example.nexam.data.ExamRoomDatabase;
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;


public class LoginSteps extends GreenCoffeeSteps
{
    private static final String INVALID_USERNAME = "guest";
    private static final String INVALID_PASSWORD = "5678";

    //TODO REMOVE
    @Given("^I see an empty login form$")
    public void iSeeAnEmptyLoginForm()
    {
        onViewWithId(R.id.date).isEmpty();
        onViewWithId(R.id.date).isEmpty();
    }

    @Given("^I login as (\\w+)$")
    public void iLoginAs$(String user)
    {
        onViewWithId(R.id.date).click();
    }

    @When("^I press the login button$")
    public void iPressTheLoginButton()
    {
        onViewWithId(R.id.date).click();
    }

    @When("^I introduce a valid username$")
    public void iIntroduceAValidUsername()
    {
        onViewWithId(R.id.date).type("");
    }

    @When("^I introduce a valid password$")
    public void iIntroduceAValidPassword()
    {
        onViewWithId(R.id.date).type("UserDatabase.USER_1.password()");
    }

    @When("^I introduce an invalid username$")
    public void iIntroduceAnInvalidUsername()
    {
        onViewWithId(R.id.date).type(INVALID_USERNAME);
    }

    @When("^I introduce an invalid password$")
    public void iIntroduceAnInvalidPassword()
    {
        onViewWithId(R.id.date).type(INVALID_PASSWORD);
    }

    @When("^I introduce as username (.+)$")
    public void iIntroduceAsUsername(String username)
    {
        onViewWithId(R.id.date).type(username);
    }

    @When("^I introduce as password (.+)$")
    public void iIntroduceAsPassword(String password)
    {
        onViewWithId(R.id.date).type(password);
    }

    @Then("^I see an error message saying 'Invalid username'$")
    public void iSeeAnErrorMessageSayingInvalidUsername()
    {
        onViewWithText(R.string.date).isDisplayed();
    }

    @Then("^I see an error message saying 'Invalid password'$")
    public void iSeeAnErrorMessageSayingInvalidPassword()
    {
        onViewWithText(R.string.date).isDisplayed();
    }

    @Then("^I see an error message saying 'Invalid credentials'$")
    public void iSeeAnErrorMessageSayingInvalidCredentials()
    {
        onViewWithText(R.string.date).isDisplayed();
    }
}