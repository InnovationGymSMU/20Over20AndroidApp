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
public class MainScreenListArrayAdapter extends ArrayAdapter<ItemListRow>  {

    private Context context;
    private int rowResourceId;
    private List<ItemListRow> items;

    public MainScreenListArrayAdapter(Context context, int layoutResourceId, List<ItemListRow> items) {
        super(context, layoutResourceId, items);
        this.context = context;
        this.rowResourceId = layoutResourceId;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(rowResourceId, parent, false);
        }
        ((TextView)row.findViewById(R.id.item_name)).setText(items.get(position).getTagName());
        return row;
    }
}
