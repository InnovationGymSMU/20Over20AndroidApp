package edu.SMU.PingItApp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class MainScreenActivity extends Activity {

    private static final String tag = "MainScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        initializeListViewAdapter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screen_action_bar_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void initializeListViewAdapter() {
        ItemListRow[] rows = new ItemListRow[] {
            new ItemListRow("Tag 1", null),
            new ItemListRow("Tag 2", null)
        };

        MainScreenListArrayAdapter adapter = new MainScreenListArrayAdapter(this, R.layout.item_selection_list_row, rows);

        ListView listView = (ListView)findViewById(R.id.main_screen_item_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemListRow row = (ItemListRow)parent.getItemAtPosition(position);
                onItemSelect(row);
            }
        });
    }

    public void addNewItem(MenuItem item) {
        Log.d(tag, "Ready to start device registration");


        Intent intent = new Intent(this, NewItemRegistrationActivity.class);
        startActivity(intent);
    }

    public void onItemSelect(ItemListRow row) {
        String viewText = row.getTagName();
        Log.d(tag, "Picked tag " + viewText);

        Intent intent = new Intent(this, FindTagActivity.class);
        startActivity(intent);
    }
}
