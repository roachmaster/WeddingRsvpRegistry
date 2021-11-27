Feature: Scenarios for Testing Wedding Rsvp Registry
  Scenario: Test a Valid Create Guest Request
    Given that the Wedding Rsvp Registry App is Running
    When the Wedding Rsvp Registry App receives a valid create Guest request
    Then the Wedding Rsvp Registry App responds with a created status
    And the Wedding Rsvp Registry App saves the Guest

  Scenario: Test a Valid Get Guest Request
    Given that the Wedding Rsvp Registry App is Running
    And the Wedding Rsvp Registry App has a saved Guest
    When the Wedding Rsvp Registry App receives a valid get Guest request
    Then the Wedding Rsvp Registry App responds with the saved Guest

  Scenario: Test a Valid Get All Guest Request
    Given that the Wedding Rsvp Registry App is Running
    And the Wedding Rsvp Registry App has saved Guests
    When the Wedding Rsvp Registry App receives a valid get all Guests request
    Then the Wedding Rsvp Registry App responds with all the saved Guests