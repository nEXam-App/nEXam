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