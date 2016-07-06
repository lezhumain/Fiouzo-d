package com.epsi.fiouzteam.fiouzoid.utils;

import com.google.gson.internal.bind.DateTypeAdapter;

import java.sql.Date;

/**
 * Created by Dju on 06/07/2016.
 */
public class LogMsg {
    private int id;
    private Date date;
    private String criticite;
    private String message;

    public LogMsg(){}

    public LogMsg(int pID, Date pDate, String pMessage, String pCriticite)
    {
        id = pID;
        date = pDate;
        criticite = pCriticite == null ? "INFO" : criticite;
        message = pMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCriticite() {
        return criticite;
    }

    public void setCriticite(String criticite) {
        this.criticite = criticite;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
