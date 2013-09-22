package edu.SMU.PingItApp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private int IDOfItem;
    private int guid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_registration);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.navigation_cancel);
        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.registration_page_title, null);
        actionBar.setCustomView(v);
        //actionBar.setTitle("                Register New Tag");

        database = new DeviceDatabase(this);
        tags = new TagAttributes(this);
        textEdited = false;
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

        SharedPreferences settings = getSharedPreferences(SplashScreenActivity.PREFS_NAME, 0);
        guid = settings.getInt("GlobalUserID", -1);
        if (guid == -1) {
            ((EditText)findViewById(R.id.registration_sn_input)).setVisibility(View.INVISIBLE);
        }
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
            if (guid != -1) {
                sendRegistrationToServer(IDOfItem, guid);
            }
            completeSuccessfulRegistration(userTagInfo);
        }
    }

    public void sendRegistrationToServer(int id, int guid) {
        String serialNumberText = ((EditText)findViewById(R.id.registration_sn_input)).getText().toString();
        int sn = Integer.parseInt(serialNumberText);

        new RegisterTagTask(id, sn, guid, this).execute(null);

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

    private class RegisterTagTask extends AsyncTask<Object, Object, String> {

        private int id;
        private int sn;
        private int guid;
        private Context context;

        public RegisterTagTask(int id, int sn, int guid, Context context) {
            this.id = id;
            this.sn = sn;
            this.guid = guid;
            this.context = context;
        }
        @Override
        public String doInBackground(Object... params) {

            HttpClient client = new DefaultHttpClient();
            try {
                HttpResponse response = client.execute(new HttpGet("http://pingit.smugym.com/registration.php?action=register&tagsn=" +
                        sn + "&tagnumber=" + id + "&guid=" + guid));
                StatusLine statusLine = response.getStatusLine();
                Log.d(tag, "Status was: " + statusLine.getStatusCode());
                if(statusLine.getStatusCode() == HttpStatus.SC_OK)
                {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    String responseString = out.toString();
                    Log.d(tag, responseString);

                    return responseString;
                }
                else {
                    response.getEntity().getContent().close();
                }
            } catch (IOException e ) {
                Log.e(tag, "Could not make the request", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if ("Success".equals(result.trim())) {
                Toast.makeText(context, "Registered in the global database!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Could not register to the global database", Toast.LENGTH_LONG).show();
            }
        }
    }



}
