package edu.SMU.PingItApp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewItemRegistrationActivity extends Activity {

    DeviceDatabase database;
    public static final int REGISTRATION_RESPONSE_SUCCESS = 1;
    Spinner itemIdSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_registration);

        database = new DeviceDatabase(this);

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

        //Check to see if the tag is already registered
        boolean isIdTaken = database.isTagAlreadyRegistered(IDOfItem);
        if (isIdTaken) {
            Toast.makeText(this, "This tag has already been registered", Toast.LENGTH_SHORT).show();
        } else {
            //If not, then add it to the database
            TagInfo tagInfo = new TagInfo(nameOfItem, IDOfItem);
            database.addTag(tagInfo);
            completeSuccessfulRegistration(tagInfo);
        }
    }

    public void completeSuccessfulRegistration(TagInfo tagInfo) {
        //Package the tag info object so that the home page can see the results
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.registration_result_id), tagInfo.getTagID());
        intent.putExtra(getString(R.string.registration_result_name), tagInfo.getTagName());
        setResult(REGISTRATION_RESPONSE_SUCCESS, intent);
        finish();
    }
    
}
