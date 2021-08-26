package com.example.pf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.pf.Activities.MyBudgetActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton myBudgetButton, myIncomeButton;
    public static final String TABLE_NAME = "my_item_table";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}