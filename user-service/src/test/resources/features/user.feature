Feature: User Management
  Everything to do with creating, saving, deleting users of the Movie Application

Scenario: Create a user
  Given the user service is running
  When a unique user with username "abc" and password "123" and email "abc@example.com" is created
  Then the user is created successfully with the correct details

Scenario: Get user by username
  Given the user service is running
  When the user with username "abc" is retrieved
  Then the user details are returned successfully