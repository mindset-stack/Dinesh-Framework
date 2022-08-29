Feature: Search for product

  @Group
  Scenario: Search for an product
    Given When user open Homepage
    When User search for "Apple" product
    Then Navigate to Searched Product Page url as "https://naveenautomationlabs.com/opencart/index.php?route=product/search&search=Apple"
