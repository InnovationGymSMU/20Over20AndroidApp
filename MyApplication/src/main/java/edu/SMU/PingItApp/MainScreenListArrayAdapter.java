package edu.SMU.PingItApp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chris on 9/19/13.
 */
public class MainScreenListArrayAdapter extends ArrayAdapter<UserTagInfo>  {

    private Context context;
    private int rowResourceId;
    private List<UserTagInfo> items;
    private TagAttributes tagAttributes;

    public MainScreenListArrayAdapter(Context context, int layoutResourceId, List<UserTagInfo> items) {
        super(context, layoutResourceId, items);
        this.context = context;
        this.rowResourceId = layoutResourceId;
        this.items = items;
        this.tagAttributes = new TagAttributes(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(rowResourceId, parent, false);
        }
        UserTagInfo rowItem = items.get(position);
        ((TextView)row.findViewById(R.id.item_name)).setText(rowItem.getTagName());
        ((ImageView)row.findViewById(R.id.item_color)).setBackgroundColor(tagAttributes.getColorForTag(rowItem.getTagID()));
        return row;
    }
}
