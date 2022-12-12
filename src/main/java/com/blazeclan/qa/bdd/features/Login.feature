Feature: Validate the login functionality

  @Login
  Scenario: Test login functionality
    Given When user open login page
    When User login to application with "loginDetails-userName" and "loginDetails-password" credentials
    Then Navigate to Home Page title as ""

  Scenario: Search for an product
    Given When user open Homepage
    When User search for "Apple" product
    Then Navigate to Searched Product Page url as "https://naveenautomationlabs.com/opencart/index.php?route=product/search&search=Apple"
