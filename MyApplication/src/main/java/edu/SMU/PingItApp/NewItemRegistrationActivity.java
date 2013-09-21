package edu.SMU.PingItApp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class NewItemRegistrationActivity extends Activity {

    private DeviceDatabase database;
    public static final int REGISTRATION_RESPONSE_SUCCESS = 1;
    private static final String tag = "NewItemRegistrationActivity";
    //Spinner itemIdSpinner;
    private LinearLayout idContainerLayout;
    private TagAttributes tags;
    private Button saveItemButton;
    private Button previousSelectedIDButton;
    private EditText itemNameInput;
    private boolean textEdited;
    private boolean spinnerEdited;
    private int IDOfItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_registration);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setIcon(R.drawable.navigation_cancel);
        actionBar.setTitle("    Register New Tag");

        database = new DeviceDatabase(this);
        tags = new TagAttributes(this);
        textEdited = false;
        spinnerEdited = true;
        IDOfItem = 0;

        idContainerLayout = (LinearLayout) findViewById(R.id.registration_id_list_view);
        List<Integer> allTags = tags.getAllAvailableTagIds();

        for (Integer i : allTags) {
            addButtonToLayout(i);
        }


        itemNameInput = (EditText) findViewById(R.id.itemNameEdit);
        itemNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String temp = itemNameInput.getText().toString();
                if (temp.matches("")) {
                    textEdited = false;
                    saveItemButton.setEnabled(false);
                    saveItemButton.setBackgroundResource(R.drawable.custom_inactive_button_design);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textEdited == true && IDOfItem != 0) {
                    saveItemButton.setEnabled(true);
                    saveItemButton.setBackgroundResource(R.drawable.custom_button_design);
                }
                textEdited = true;
            }
        }
        );


        saveItemButton = (Button) findViewById(R.id.saveItemButton);
        saveItemButton.setEnabled(false);
        saveItemButton.setBackgroundResource(R.drawable.custom_inactive_button_design);

    }

    private void addButtonToLayout(Integer i) {
        Button button = new Button(this);
        button.setText(i.toString());
        button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0f);
        button.setMaxWidth(10);
        button.setMaxHeight(10);

        button.setBackgroundResource(R.drawable.transparent_button_design);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button chosenView = (Button) view;
                view.setBackgroundResource(R.drawable.custom_inactive_button_design);
                if (previousSelectedIDButton != null) {
                    previousSelectedIDButton.setBackgroundResource(R.drawable.transparent_button_design);
                }
                previousSelectedIDButton = chosenView;
                Log.d(tag, "Selected item " + chosenView.getText());
                saveSelectedButtonID(Integer.parseInt(chosenView.getText().toString()));
            }
        });
        idContainerLayout.addView(button);


    }

    private void saveSelectedButtonID(int tagID) {
        IDOfItem = tagID;
        if (textEdited == true) {
            saveItemButton.setEnabled(true);
            saveItemButton.setBackgroundResource(R.drawable.custom_button_design);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_item_registration, menu);
        return true;
    }

    public void saveNewItem(View view) {
        EditText itemName = (EditText) findViewById(R.id.itemNameEdit);
        String nameOfItem = itemName.getText().toString();
        Log.d(tag, nameOfItem);
        Log.d(tag, "" + IDOfItem);

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
