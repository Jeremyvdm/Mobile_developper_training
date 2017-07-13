package com.formation.appli.bruxellesparcourbd.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.formation.appli.bruxellesparcourbd.DB.UserDAO;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeremyvdm on 02/07/2017.
 */

public class User implements Parcelable {
    //region POJO
    private String _userId;
    private String lastName;
    private String firstName;
    private String userName;
    private String email;
    private String password;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public User(){
        //Constructeur vide nécéssaire lorsqu'on appelle la méthode databaseSnapshot.getValue(User.class)
    }

    public User(String lastName, String firstName, String userName, String email, String password) {
        this._userId = null;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String userID, String lastName, String firstName, String userName, String email, String password) {
        this._userId = userID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }


    public String get_userId() {
        return _userId;
    }

    public void set_userId(String _userId) {
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
        dest.writeString(_userId);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(email);
        dest.writeString(password);
    }

    protected User(Parcel in) {
        _userId = in.readString();
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

    //region database
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put(UserDAO.COLUMN_USERID, _userId);
        result.put(UserDAO.COLUMN_LAST_NAME, lastName);
        result.put(UserDAO.COLUMN_FIRST_NAME, firstName);
        result.put(UserDAO.COLUMN_USER_NAME, userName);
        result.put(UserDAO.COLUMN_EMAIL, userName);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    //endregion
}
