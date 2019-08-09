package com.indore.api;

/**
 * @author Amit Khandelwal
 */
public final class UserSearchResult {
    private final String firstName;
    private final String lastName;
    private final String emailId;

    private String password = "";
    private float score;
    private String userId;

    public UserSearchResult(String firstName, String lastName, String emailId,String password,String userId,float score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId= emailId;

        this.password=password;
        this.userId = userId;
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

    public String getemailId() { return emailId; }

    public String getuserId() { return userId ;}
    public String getPassword()  { return password;}

}
