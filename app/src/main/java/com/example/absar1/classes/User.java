package com.example.absar1.classes;

import java.util.ArrayList;

public class User {
    private String email;
    private ArrayList<String> favoriteArrayList;

    public User(String email) {
        this.email = email;
        favoriteArrayList=new ArrayList<String>();
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getFavoriteArrayList() {
        return favoriteArrayList;
    }

    public void setFavoriteArrayList(ArrayList<String> favoriteArrayList) {
        this.favoriteArrayList = favoriteArrayList;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", favoriteArrayList=" + favoriteArrayList +
                '}';
    }
}
