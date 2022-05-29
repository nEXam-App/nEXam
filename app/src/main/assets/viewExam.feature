Feature: Use Case: View Exam
  As a USER
  I want to view an existing exam.
  Therefore I can select an existing exam.

  Scenario: Selecting an exam from the list
    Given I am on the dashboard screen
    When  I select the exam called 'Compilerbau'
    Then  I see the detail screen for 'Compilerbau'
    And   I take a screenshot named 'viewExam'
    And   I see the subject is 'Compilerbau'
    And   I see the date is '29/05/2022'

  Scenario: Selecting an exam from the list
    Given I am on the dashboard screen
    When  I select the exam called 'Mathe'
    Then  I see the detail screen for 'Mathe'
    And   I see the subject is 'Mathe'
    And   I see the date is '29/05/2022'