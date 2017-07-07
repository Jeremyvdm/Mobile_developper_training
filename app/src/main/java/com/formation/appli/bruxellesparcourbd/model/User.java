package com.formation.appli.bruxellesparcourbd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jeremyvdm on 02/07/2017.
 */

public class User implements Parcelable {
    //region POJO
    private int _userId;
    private String lastName;
    private String firstName;
    private String userName;
    private String email;
    private String password;

    public User(String lastName, String firstName, String userName, String email, String password) {
        this._userId = -1;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(int userID, String lastName, String firstName, String userName, String email, String password) {
        this._userId = userID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }


    public int get_userId() {
        return _userId;
    }

    public void set_userId(int _userId) {
        this._userId = _userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //endregion

    //region Parsable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(_userId);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(email);
        dest.writeString(password);
    }

    protected User(Parcel in) {
        _userId = in.readInt();
        lastName = in.readString();
        firstName = in.readString();
        userName = in.readString();
        email = in.readString();
        password = in.readString();
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

    //endregion
}
