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
