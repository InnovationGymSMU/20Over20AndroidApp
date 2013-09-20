package edu.SMU.PingItApp;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NewItemRegistrationActivity extends Activity {

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
    }

    
}
