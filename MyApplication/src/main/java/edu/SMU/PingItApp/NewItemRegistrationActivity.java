package edu.SMU.PingItApp;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class NewItemRegistrationActivity extends Activity {

    Spinner itemIdSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_registration);

        itemIdSpinner = (Spinner) findViewById(R.id.itemIDSelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_id_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemIdSpinner.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_item_registration, menu);
        return true;
    }

    public void saveNewItem(View view)
    {
        EditText itemName = (EditText) findViewById(R.id.itemNameEdit);
        String nameOfItem = itemName.getText().toString();
        Log.d("NewItemRegistrationActivity", nameOfItem);
        int IDOfItem = itemIdSpinner.getSelectedItemPosition();
        Log.d("NewItemRegistrationActivity", "" + IDOfItem);
    }

    
}
