package com.example.pf.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pf.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton myBudgetButton, myIncomeButton;
    public static final String TABLE_NAME = "my_item_table";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private EditText edt_firstName;
    private EditText edt_lastName;
    private CheckBox checkbox_remember;

    private TextView tv_hi;
    private String firstName;
    private String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_hi = findViewById(R.id.tv_hi);

        showWelcomeDialog();

        myBudgetButton = findViewById(R.id.button_myBudget);
        myIncomeButton = findViewById(R.id.button_budgetIncome);

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        /**
         * Takes the user from Main Activity to the Budget Activity
         * */
        myBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyBudgetActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showWelcomeDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.welcome_layout, null);
        dialog.setView(v);

        AlertDialog myDialog = dialog.create();
        myDialog.setCancelable(false);

        AppCompatButton btn_login = v.findViewById(R.id.btn_login);
        checkbox_remember = v.findViewById(R.id.checkbox_remember);
        edt_firstName = v.findViewById(R.id.edt_firstname);
        edt_lastName = v.findViewById(R.id.edt_lastname);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        sharedPreferencesCheck();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myDialog.dismiss();

                if (checkbox_remember.isChecked()) {
                    firstName = edt_firstName.getText().toString();
                    lastName = edt_lastName.getText().toString();

                    editor.putString(getString(R.string.firstName_key), firstName);
                    editor.commit();

                    editor.putString(getString(R.string.lastName_key), lastName);
                    editor.commit();

                    editor.putString(getString(R.string.checkbox_key), "True");
                    editor.commit();
                }
                else {
                    editor.putString(getString(R.string.firstName_key), "");
                    editor.commit();

                    editor.putString(getString(R.string.lastName_key), "");
                    editor.commit();

                    editor.putString(getString(R.string.checkbox_key), "False");
                    editor.commit();
                }

                firstName = edt_firstName.getText().toString();
                lastName = edt_lastName.getText().toString();

                editor.putString(getString(R.string.firstName_key), firstName);
                editor.commit();

                editor.putString(getString(R.string.lastName_key), lastName);
                editor.commit();

                firstName = preferences.getString(getString(R.string.firstName_key), firstName);

                tv_hi.setText("Hi " + firstName + " " + lastName);

                myDialog.dismiss();
            }
        });

        myDialog.show();
    }

    private void sharedPreferencesCheck() {

        String mCheckbox = preferences.getString(getString(R.string.checkbox_key), "False");
        String mFirstName = preferences.getString(getString(R.string.firstName_key), "");  //empty default value
        String mLastName = preferences.getString(getString(R.string.lastName_key), "");  //empty default value

        //Set the values to the TextViews.
        edt_firstName.setText(mFirstName);
        edt_lastName.setText(mLastName);

        if (mCheckbox.equals("True")) {
            checkbox_remember.setChecked(true);
        } else {
            checkbox_remember.setChecked(false);
        }
    }
}