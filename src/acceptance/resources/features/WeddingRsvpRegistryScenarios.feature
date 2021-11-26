Feature: Scenarios for Testing Wedding Rsvp Registry
  Scenario: Test a Valid Create Guest
    Given that the Wedding Rsvp Registry App is Running
    When the Wedding Rsvp Registry App receives a valid create Guest request
    Then the Wedding Rsvp Registry App responds with a created status
    And the Wedding Rsvp Registry App saves the Guest