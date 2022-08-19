Feature: Validate the login functionality

  Scenario: Test login functionality
    Given When user open login page
    When User login to application with "dineshkumar.icon.dk@gmail.com" and "Dinnu@4318" credentials
    Then Navigate to Home Page title as ""
