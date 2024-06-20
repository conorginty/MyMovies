Feature: Movie List to User Interaction Management

  Scenario: Create a new movie list for a user
    Given a user with ID 1
    When the user creates a movie list named "My Movie List"
    Then the movie list is created successfully

  Scenario: Add a movie to a movie list
    Given a movie list with ID 23 for user ID 1
    When the user adds a movie with ID 456 to the movie list
    Then the movie is added to the movie list

  Scenario: Delete a movie from a movie list
    Given a movie list with ID 23 for user ID 1 containing movie ID 456
    When the user deletes the movie with ID 456 from the movie list
    Then the movie is removed from the movie list

  Scenario: Get movie IDs from a movie list
    Given a movie list with ID 23 for user ID 1 containing movies with IDs 4, 5, and 6
    When the user requests the movie IDs from the movie list
    Then the response contains movie IDs 4, 5, and 6
