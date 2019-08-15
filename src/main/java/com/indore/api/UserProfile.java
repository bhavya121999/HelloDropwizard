package com.indore.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.MoreObjects;

public class UserProfile  {
    private String userId;
    private String address;
    private String currentCity;
    private String hometown;
    private String landmark;
    private String pincode;
    private String education;
    private String highSchool;
    private String college;
    private String socialLink;
    private String language;
    private String aboutYou;
    private String otherNames;



    private String hobbies;
    private String professionalSkills;
    private String musicArtist;
    private String bookAuthor;
    private String programmes;
    private String sportsTeam;
    private String sportsPeople;
    private String favouriteQuotes;
    private String lifeEvents;
    private long createdDate;

    public UserProfile(){

    }


    public UserProfile(String userId, String address, String currentCity, String hometown, String landmark,
                       String pincode, String education, String highSchool, String college, String socialLink,
                       String language, String aboutYou, String otherNames, String hobbies, String professionalSkills,
                       String musicArtist, String bookAuthor, String programmes, String sportsTeam, String sportsPeople,
                       String favouriteQuotes, String lifeEvents, long createdDate) {
        this.userId = userId;
        this.address = address;
        this.currentCity = currentCity;
        this.hometown = hometown;
        this.landmark = landmark;
        this.pincode = pincode;
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
        this.createdDate=createdDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHighSchool() {
        return highSchool;
    }

    public void setHighSchool(String highSchool) {
        this.highSchool = highSchool;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getSocialLink() {
        return socialLink;
    }

    public void setSocialLink(String socialLink) {
        this.socialLink = socialLink;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAboutYou() {
        return aboutYou;
    }

    public void setAboutYou(String aboutYou) {
        this.aboutYou = aboutYou;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getProfessionalSkills() {
        return professionalSkills;
    }

    public void setProfessionalSkills(String professionalSkills) {
        this.professionalSkills = professionalSkills;
    }

    public String getMusicArtist() {
        return musicArtist;
    }

    public void setMusicArtist(String musicArtist) {
        this.musicArtist = musicArtist;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getProgrammes() {
        return programmes;
    }

    public void setProgrammes(String programmes) {
        this.programmes = programmes;
    }

    public String getSportsTeam() {
        return sportsTeam;
    }

    public void setSportsTeam(String sportsTeam) {
        this.sportsTeam = sportsTeam;
    }

    public String getSportsPeople() {
        return sportsPeople;
    }

    public void setSportsPeople(String sportsPeople) {
        this.sportsPeople = sportsPeople;
    }

    public String getFavouriteQuotes() {
        return favouriteQuotes;
    }

    public void setFavouriteQuotes(String favouriteQuotes) {
        this.favouriteQuotes = favouriteQuotes;
    }

    public String getLifeEvents() {
        return lifeEvents;
    }

    public void setLifeEvents(String lifeEvents) {
        this.lifeEvents = lifeEvents;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }




    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("address", address)
                .add("currentCity", currentCity)
                .add("hometown", hometown)
                .add("landmark", landmark)
                .add("pincode", pincode)
                .add("education",education)
                .add("highSchool",highSchool)
                .add("college",college)
                .add("socialLink",socialLink)
                .add("language",language)
                .add("aboutYou",aboutYou)
                .add("otherNames",otherNames)
                .add("hobbies",hobbies) 
                .add("professionalSkills",professionalSkills)
                .add("musicArtist",musicArtist)
                .add("bookAuthor",bookAuthor)
                .add("programmes",programmes)
                .add("sportsTeam",sportsTeam)
                .add("sportsPeople",sportsPeople)
                .add("favouriteQuotes",favouriteQuotes)
                .add("lifeEvents",lifeEvents)
                .add("createdDate", createdDate)
                .toString();
    }


}
