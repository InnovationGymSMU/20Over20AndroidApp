package edu.SMU.PingItApp;

import android.app.ActionBar;
import android.app.Activity;

/**
 * Created by Chris on 11/2/13.
 */
public class ActionBarHelper {

    public static void setLeftActionBar(Activity activity, int imageID) {
        ActionBar actionBar = activity.getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(imageID);
    }
}
