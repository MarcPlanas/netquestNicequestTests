Feature: Nicequest login

  @happyPath
  Scenario: Log in with valid credentials
    Given I install the application
    When I open the app
    And I skip the options
    And I log in with valid email and password
    Then the login should succeed
    And I uninstall the application

  @negative
  Scenario: Reject invalid credentials
    Given I install the application
    When I open the app
    And I skip the options
    And I log in with invalid email and password
    Then the login should be rejected
    And I uninstall the application

  @install @skip
  Scenario: Install and leave the app open
    Given I install the application
    When I open the app

  @uninstall @skip
  Scenario: Uninstall the application
    Given I uninstall the application

  @login @skip
  Scenario: Log in
    Given I log in with valid email and password
