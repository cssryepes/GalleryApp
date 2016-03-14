package com.kogi.galleryapp.domain.entities;

public class User {

    private String id;
    private String username;
    private String fullName;
    private String profilePricture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePricture() {
        return profilePricture;
    }

    public void setProfilePricture(String profilePricture) {
        this.profilePricture = profilePricture;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", profilePricture='" + profilePricture + '\'' +
                '}';
    }
}
