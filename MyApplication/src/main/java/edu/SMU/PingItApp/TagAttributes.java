package edu.SMU.PingItApp;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 9/20/13.
 */
public class TagAttributes {
    private static final String tag = "TagAttributes";
    private static Map<Integer, String> colorKeys = null;
    private Context context;

    public TagAttributes(Context context) {
        this.context = context;
        if (colorKeys == null) {
            loadColorKeys();
        }
    }

    private void loadColorKeys() {
        colorKeys = new HashMap<Integer, String>();

        try {
            InputStream inputStream = context.getAssets().open("TagData.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] components = line.split(" ");
                Integer id = Integer.parseInt(components[0]);
                colorKeys.put(id, components[1]);
            }

        } catch (IOException e) {
            Log.e(tag, "Error opening the tags file", e);
        }
    }

    public String getHexColorForTag(int tagID) {
        return colorKeys.get(tagID);
    }

    public int getColorForTag(int tagID) {
        if (!colorKeys.containsKey(tagID)) {
            return Color.parseColor("#FFFF0000");
        }
        return Color.parseColor(colorKeys.get(tagID));
    }
}
