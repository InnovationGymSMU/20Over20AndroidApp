package edu.SMU.PingItApp;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chris on 9/19/13.
 */
public class UserTagInfo {

    private static final String tag = "UserTagInfo";
    private String tagName;
    private int tagID;
    private Date registrationDate;

    public UserTagInfo(String tagName, int tagID) {
        this.tagName = tagName;
        this.tagID = tagID;
        this.registrationDate = null;
    }

    public UserTagInfo(String tagName, int tagID, Date registrationDate) {
        this.tagName = tagName;
        this.tagID = tagID;
        this.registrationDate = registrationDate;
    }

    public UserTagInfo(String tagName, int tagID, String registrationDate) {
        this.tagName = tagName;
        this.tagID = tagID;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/YYYY");
        try {
            this.registrationDate = format.parse(registrationDate);
        } catch (ParseException e) {
            this.registrationDate = null;
            Log.e(tag, "Could not parse the date string: " + registrationDate, e);
        }

    }

    public String getTagName() {
        return tagName;
    }

    public int getTagID() {
        return tagID;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
