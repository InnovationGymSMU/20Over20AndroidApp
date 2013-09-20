package edu.SMU.PingItApp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

public class NewItemRegistrationActivity extends Activity {

    DeviceDatabase database;
    public static final int REGISTRATION_RESPONSE_SUCCESS = 1;
    Spinner itemIdSpinner;
    Button saveItemButton;
    EditText itemNameInput;
    boolean textEdited;
    boolean spinnerEdited;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_registration);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(android.R.drawable.ic_menu_close_clear_cancel);

        database = new DeviceDatabase(this);
        textEdited = false;
        spinnerEdited = false;

        itemIdSpinner = (Spinner) findViewById(R.id.itemIDSelector);

        String[] itemIdValues = getResources().getStringArray(R.array.item_id_values);



        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, itemIdValues) {
            @Override
            public View getView(int position, View convertView,ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }

            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
        };

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                //R.array.item_id_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemIdSpinner.setAdapter(adapter);


        itemNameInput = (EditText) findViewById(R.id.itemNameEdit);
        itemNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {
                String temp = itemNameInput.getText().toString();
                if(temp.matches(""))
                {
                    textEdited = false;
                    saveItemButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(textEdited == true && spinnerEdited == true)
                {
                    saveItemButton.setEnabled(true);
                }
                textEdited = true;
            }
        }
        );

        itemIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(textEdited == true)
                {
                    saveItemButton.setEnabled(true);
                }
                if(i != 0)
                {
                    spinnerEdited = true;
                }
                if(i == 0)
                {
                    saveItemButton.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                spinnerEdited = false;
            }
        });

        saveItemButton = (Button) findViewById(R.id.saveItemButton);
        saveItemButton.setEnabled(false);

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
        //boolean isIdTaken = database.isTagAlreadyRegistered(IDOfItem);
        UserTagInfo tagInfo = database.getTagInfoFromId(IDOfItem);
        if (tagInfo != null) {
            Toast.makeText(this, "This tag is already registered as " + tagInfo.getTagName(), Toast.LENGTH_SHORT).show();
        } else {
            //If not, then add it to the database
            UserTagInfo userTagInfo = new UserTagInfo(nameOfItem, IDOfItem);
            database.addTag(userTagInfo);
            completeSuccessfulRegistration(userTagInfo);
        }
    }

    public void completeSuccessfulRegistration(UserTagInfo userTagInfo) {
        //Package the tag info object so that the home page can see the results
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.registration_result_id), userTagInfo.getTagID());
        intent.putExtra(getString(R.string.registration_result_name), userTagInfo.getTagName());
        setResult(REGISTRATION_RESPONSE_SUCCESS, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }

}
