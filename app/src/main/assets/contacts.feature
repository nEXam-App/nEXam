Feature: Exams and Details

  Scenario: User launches the app.
    Given the user has opened the app.
    When the app has launched.
    Then a user should see a list of exams with “Mathe” in the list.

  Scenario: The app has launched.
    Given the user has launched the app.
    And a list of exams is displayed.
    When the user taps on “Mathe”.
    Then the app should navigate to the details screen.
    And the text “Mathe” should be visible.

#  TODO remove if not needed: FROM GREENCOFFE UI
Feature: List of contacts screen
  In order to see the list of contacts
  As a user of the app
  I want to access the contact list screen

  Scenario: Selecting a contact from the list
    Given I am on the contacts screen
    And   I take a screenshot named 'contacts'
    When  I select the contact called 'James Houghton'
    Then  I see the detail screen for 'James Houghton'

  Scenario: Selecting a contact from the list
    Given  I am on the contacts screen
    When  I select the contact called 'John Smith'
    Then  I see the detail screen for 'John Smith'
