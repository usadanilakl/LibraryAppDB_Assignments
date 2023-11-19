
Feature: As a data consumer, I want UI and DB book information are match.

  Scenario: Verify book information with DB
    Given the "librarian" on the home page
    And the user navigates to "Books" page
    When the user searches for "Clean Code" book
    And  the user clicks edit book button
    Then book information must match the Database

  @DK @wip @db @ui
  Scenario: Verify book information with DB DK
    Given the "librarian" on the home page DK
    And the user navigates to "Books" page DK
    When the user searches for "Clean Code" book DK
    And  the user clicks edit book button DK
    Then book information must match the Database DK