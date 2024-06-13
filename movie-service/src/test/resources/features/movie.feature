Feature: Movie Management

  Scenario: Find movies between specific release years
    Given the following movies exist:
      | title          | releaseYear |
      | Movie A        | 2000        |
      | Movie B        | 2005        |
      | Movie C        | 2010        |
    When movies released between years 2000 and 2005 are requested
    Then the following movies should be retrieved:
      | title          | releaseYear |
      | Movie A        | 2000        |
      | Movie B        | 2005        |