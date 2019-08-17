package com.indore.api;

/**
 * UserSearchResult to generate getters for User Resource.
 * getter is a method that reads value of a variable.
 *
 * @author Amit Khandelwal
 */
public final class UserSearchResult {
    private final String firstName;
    private final String lastName;
    private final String emailId;
    private final float score;
    private final String userId;

    public UserSearchResult(String firstName, String lastName, String emailId, String userId, float score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.score = score;
        this.userId = userId;
    }

    /**
     * @return FirstName of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return LastName of the user.
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * @return Score of each document with a positive floating-point number known as the _score.
     */

    public float getScore() {
        return score;
    }

    /**
     * @return emailId of the user.
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @return userId of the user.
     */
    public String getUserId() {
        return userId;
    }
}
