package com.epsi.fiouzteam.fiouzoid.model;

public class User
{

    public User() {}
    public User(int id, String firstName, String lastName, String nickName)
    {
        _id = id;
        _firstName = firstName;
        _lastName = lastName;
        _nickName = nickName;
    }

    private String _firstName;
    private String _lastName;
    private String _nickName;
    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_nickName() {
        return _nickName;
    }

    public void set_nickName(String _nickName) {
        this._nickName = _nickName;
    }

    public String get_lastName() {
        return _lastName;
    }

    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }

    public String get_firstName() {
        return _firstName;
    }

    public void set_firstName(String _firstName) {this._firstName = _firstName;}

    @Override
    public String toString() {
        return "User{" +
                "_firstName='" + _firstName + '\'' +
                ", _lastName='" + _lastName + '\'' +
                ", _nickName='" + _nickName + '\'' +
                ", _id=" + _id +
                '}';
    }

}