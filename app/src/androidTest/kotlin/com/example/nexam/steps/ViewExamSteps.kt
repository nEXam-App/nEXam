package com.example.nexam.steps

import com.example.nexam.R
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps
import com.mauriciotogneri.greencoffee.annotations.Then

@Suppress("unused")
class ViewExamSteps : GreenCoffeeSteps() {
    @Then("^I see the detail screen for '([\\w| ]+)'$")
    fun `iSeeTheDetailScreenFor$`(exam: String?) {
        onViewWithText(exam).isDisplayed
    }

    @Then("^I see the subject is '([\\w| ]+)'$")
    fun iSeeTheSubjectIs(name: String?) {
        onViewWithId(R.id.exam_name).contains(name)
    }

    @Then("^I see the date is '([\\w| ]+)'$")
    fun iSeeTheDateIs(date: String?) {
        onViewWithId(R.id.date).contains(date)
    }
}