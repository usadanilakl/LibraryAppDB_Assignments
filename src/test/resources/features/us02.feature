
Feature: As a librarian, I want to know borrowed books number

  Scenario: verify the total amount of borrowed books
    Given the "librarian" on the home page
    When the librarian gets borrowed books number
    Then borrowed books number information must match with DB

  @DK @db @ui
  Scenario: verify the total amount of borrowed books DK
    Given the "librarian" on the home page DK
    When the librarian gets borrowed books number DK
    Then borrowed books number information must match with DB DK