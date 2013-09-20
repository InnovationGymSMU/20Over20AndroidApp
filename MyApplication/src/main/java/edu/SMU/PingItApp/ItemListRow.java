package edu.SMU.PingItApp;

import android.widget.ImageView;

/**
 * Created by Chris on 9/19/13.
 */
public class ItemListRow {
    private String tagName;
    private ImageView image;

    public ItemListRow() {
        image = null;
        tagName = null;
    }

    public ItemListRow(String tagName, ImageView image) {
        this.tagName = tagName;
        this.image = image;
    }

    public String getTagName() {
        return tagName;
    }

    public ImageView getImage() {
        return image;
    }
}
