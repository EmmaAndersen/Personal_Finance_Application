package com.example.pf.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.pf.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton myBudgetButton, myIncomeButton;
    public static final String TABLE_NAME = "my_item_table";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    private void showWelcomeDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.welcome_layout, null);
        dialog.setView(v);

        AlertDialog myDialog = dialog.create();
        myDialog.setCancelable(false);

        AppCompatButton btn_login = v.findViewById(R.id.btn_login);
        EditText edt_firstName = v.findViewById(R.id.edt_firstname);
        EditText edt_lastName = v.findViewById(R.id.edt_lastname);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.show();
    }
}