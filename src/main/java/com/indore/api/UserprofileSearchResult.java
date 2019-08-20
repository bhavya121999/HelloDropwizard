package com.indore.api;

/**
 *   UserprofileSearchResult to generate getters for UserProfile Resource.
 *   getter is a method that reads value of a variable.
 */

public final class UserprofileSearchResult {
    private final String userId;
    private final String address;
    private final String city;
    private final String hometown;
    private final String landmark;

    private final String education;
    private final String highSchool;
    private final String college;
    private final String socialLink;
    private final String language;
    private final String aboutYou;
    private final String otherNames;
    private final String hobbies;
    private final String professionalSkills;
    private final String musicArtist;
    private final String bookAuthor;
    private final String programmes;
    private final String sportsTeam;
    private final String sportsPeople;
    private final String favouriteQuotes;
    private final String lifeEvents;
    private final float score;

    public UserprofileSearchResult(String userId, String address, String city, String hometown, String landmark, String education, String highSchool, String college, String socialLink, String language, String aboutYou, String otherNames, String hobbies, String professionalSkills, String musicArtist, String bookAuthor, String programmes, String sportsTeam, String sportsPeople, String favouriteQuotes, String lifeEvents, float score) {
        this.userId = userId;
        this.address = address;
        this.city = city;
        this.hometown = hometown;
        this.landmark = landmark;

        this.education = education;
        this.highSchool = highSchool;
        this.college = college;
        this.socialLink = socialLink;
        this.language = language;
        this.aboutYou = aboutYou;
        this.otherNames = otherNames;
        this.hobbies = hobbies;
        this.professionalSkills = professionalSkills;
        this.musicArtist = musicArtist;
        this.bookAuthor = bookAuthor;
        this.programmes = programmes;
        this.sportsTeam = sportsTeam;
        this.sportsPeople = sportsPeople;
        this.favouriteQuotes = favouriteQuotes;
        this.lifeEvents = lifeEvents;
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getHometown() {
        return hometown;
    }

    public String getLandmark() {
        return landmark;
    }


    public String getEducation() {
        return education;
    }

    public String getHighSchool() {
        return highSchool;
    }

    public String getCollege() {
        return college;
    }

    public String getSocialLink() {
        return socialLink;
    }

    public String getLanguage() {
        return language;
    }

    public String getAboutYou() {
        return aboutYou;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getProfessionalSkills() {
        return professionalSkills;
    }

    public String getMusicArtist() {
        return musicArtist;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getProgrammes() {
        return programmes;
    }

    public String getSportsTeam() {
        return sportsTeam;
    }

    public String getSportsPeople() {
        return sportsPeople;
    }

    public String getFavouriteQuotes() {
        return favouriteQuotes;
    }

    public String getLifeEvents() {
        return lifeEvents;
    }
}
