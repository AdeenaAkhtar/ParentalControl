package com.example.parentalcontrol;

public class User {
    private String Username;
    private String FirstName;
    private String LastName;
    private String Password;
    private String Number;
    private int Age;
    private boolean Parent;

    public User() {

    }

    public User(String username, String firstName, String lastName, String password, String number, int age, boolean parent) {
        Username = username;
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        Number = number;
        Age = age;
        Parent = parent;
    }

    public boolean isParent() {
        return Parent;
    }

    public void setParent(boolean parent) {
        Parent = parent;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
}