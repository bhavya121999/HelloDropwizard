package com.indore.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.MoreObjects;

public class UserProfile {
    private String userId ;//required
    private  String address;//optional
    private  String currentCity;//optional
    private  String hometown;//optional
    private String landmark;//optional
    private  String pincode;//optional
    private  String education;//optional
    private  String highSchool;//optional
    private  String college;//optional
    private String socialLink;//optional
    private  String language;//optional
    private String aboutYou;//optional
    private  String otherNames;//optional
    private  String hobbies;//optional
    private  String professionalSkills;//optional
    private  String musicArtist;//optional
    private  String bookAuthor;//optional
    private  String programmes;//optional
    private  String sportsTeam;//optional
    private  String sportsPeople;//optional
    private  String favouriteQuotes;//optional
    private  String lifeEvents;//optional
    private  long createdDate;


    private UserProfile(UserProfileBuilder builder) {
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
        this.createdDate = createdDate;
    }


    public String getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public String getHometown() {
        return hometown;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getPincode() {
        return pincode;
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

    public long getCreatedDate() {
        return createdDate;
    }

    public String toString() {
        return "UserProfile: " + this.userId + ", " + this.address + ", " + this.currentCity + ", " + this.hometown + ", " +
                "" + this.landmark + ",  " + this.pincode + ", " + this.education + ", " + this.highSchool + ", " + this.college + ", " +
                "" + this.socialLink + ", " + this.language + ", " + this.aboutYou + ", " + this.otherNames + ", " + this.hobbies + "," +
                " " + this.professionalSkills + ", " + this.musicArtist + ", " + this.bookAuthor + ", " + this.programmes + ", " +
                "" + this.sportsTeam + " , " + this.sportsPeople + ", " + this.favouriteQuotes + ", " + this.lifeEvents + ", " + this.createdDate;
    }

    public static class UserProfileBuilder {
        private final String userId;
        private  String address;
        private  String currentCity;
        private  String hometown;
        private  String landmark;
        private  String pincode;
        private  String education;
        private  String highSchool;
        private  String college;
        private  String socialLink;
        private  String language;
        private  String aboutYou;
        private  String otherNames;
        private  String hobbies;
        private  String professionalSkills;
        private  String musicArtist;
        private  String bookAuthor;
        private  String programmes;
        private  String sportsTeam;
        private  String sportsPeople;
        private  String favouriteQuotes;
        private  String lifeEvents;
        private  long createdDate;

        public UserProfileBuilder(String userId) {
            this.userId = userId;
        }
        public UserProfileBuilder address(String address) {
            this.address = address;
            return this;
        }
        public UserProfileBuilder currentCity(String currentCity) {
            this.currentCity = currentCity;
            return this;
        }
        public UserProfileBuilder hometown(String hometown) {
            this.hometown = hometown;
            return this;
        }
        public UserProfileBuilder landmark(String landmark) {
            this.landmark = landmark;
            return this;
        }
        public UserProfileBuilder pincode(String pincode) {
            this.pincode = pincode;
            return this;
        }

        public UserProfileBuilder education(String education) {
            this.education = education;
            return this;
        }
        public UserProfileBuilder highSchool(String highSchool) {
            this.highSchool = highSchool;
            return this;
        }
        public UserProfileBuilder college(String college) {
            this.college = college;
            return this;
        }
        public UserProfileBuilder socialLink(String socialLink) {
            this.socialLink = socialLink;
            return this;
        } public UserProfileBuilder language(String language) {
            this.language = language;
            return this;
        }
        public UserProfileBuilder aboutYou(String aboutYou) {
            this.aboutYou = aboutYou;
            return this;
        }
        public UserProfileBuilder otherNames(String otherNames) {
            this.otherNames = otherNames;
            return this;
        }
        public UserProfileBuilder hobbies(String hobbies) {
            this.hobbies = hobbies;
            return this;
        } public UserProfileBuilder professionalSkills(String professionalSkills) {
            this.professionalSkills = professionalSkills;
            return this;
        }
        public UserProfileBuilder musicArtist(String musicArtist) {
            this.musicArtist = musicArtist;
            return this;
        }
        public UserProfileBuilder bookAuthor(String bookAuthor) {
            this.bookAuthor = bookAuthor;
            return this;
        }
        public UserProfileBuilder programmes(String programmes) {
            this.programmes = programmes;
            return this;
        }
        public UserProfileBuilder sportsTeam(String sportsTeam) {
            this.sportsTeam = sportsTeam;
            return this;
        }
        public UserProfileBuilder sportsPeople(String sportsPeople) {
            this.sportsPeople = sportsPeople;
            return this;
        }
        public UserProfileBuilder favouriteQuotes(String favouriteQuotes) {
            this.favouriteQuotes = favouriteQuotes;
            return this;
        }
        public UserProfileBuilder lifeEvents(String lifeEvents) {
            this.lifeEvents = lifeEvents;
            return this;
        }
        public UserProfileBuilder createdDate(long createdDate) {
            this.createdDate = createdDate;
            return this;
        }
            public UserProfile build() {
            UserProfile user =  new UserProfile(this);
            validateUserObject(user);
            return user;
        }
        private void validateUserObject(UserProfile user) {

        }
    }
}