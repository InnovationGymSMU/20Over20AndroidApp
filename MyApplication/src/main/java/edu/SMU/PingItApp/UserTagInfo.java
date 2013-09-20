package edu.SMU.PingItApp;

/**
 * Created by Chris on 9/19/13.
 */
public class UserTagInfo {
    private String tagName;
    private int tagID;

    public UserTagInfo(String tagName, int tagID) {
        this.tagName = tagName;
        this.tagID = tagID;
    }

    public String getTagName() {
        return tagName;
    }

    public int getTagID() {
        return tagID;
    }
}
