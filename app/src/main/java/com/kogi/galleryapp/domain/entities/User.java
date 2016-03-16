package com.kogi.galleryapp.domain.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String id;
    private String username;
    private String fullName;
    private String profilePricture;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(fullName);
        dest.writeString(profilePricture);
    }

    public User() {
    }

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        fullName = in.readString();
        profilePricture = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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
