package com.indore.api;

/**
 * @author Amit Khandelwal
 */
public final class UserSearchResult {
    private final String firstName;
    private final String lastName;
    private final float score;

    public UserSearchResult(String firstName, String lastName, float score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public float getScore() {
        return score;
    }
}
