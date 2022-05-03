Feature: Use Case Show Dashboard
  As a user
  I want to open the app or navigate to the dashboard from another view.
  Therefore, I will need to open the app or click the corresponding button.

  Scenario: Selecting an exam from the list
    Given I am on the dashboard screen
    And I take a screenshot named 'exams'
    When I select the exam called 'Compilerbau'
    Then I see the detail screen for 'Compilerbau'