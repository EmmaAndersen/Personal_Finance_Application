package com.example.pf.Activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pf.Database.Item;
import com.example.pf.Fragments.ExpenditureFragment;
import com.example.pf.Fragments.IncomeFragment;
import com.example.pf.MainViewModel;
import com.example.pf.MyAdapter;
import com.example.pf.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MyBudgetActivity extends AppCompatActivity implements MyAdapter.onMyItemClickListener {

    AppCompatButton cancelBtn, cancelBtn2;
    AppCompatButton saveBtn, deleteBtn;
    AppCompatButton incomeBtn, expenditureBtn;
    AppCompatButton swapIncomeBtn, swapExpenditureBtn, swapAllBtn;
    EditText amount;
    Spinner spinner;
    String budget;
    String myItem;
    FloatingActionButton fab;
    RecyclerView recyclerView, recyclerViewIncome;
    ImageView categoryTV;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private TextView totalAmount;
    private int totalKR;

    private MyAdapter myAdapter, incomeAdapter;
    public MainViewModel mainViewModel;
    AlertDialog myDialog, myDialogDelete;
    Item itemClass;

    int idPosition;
    private ImageView upperImage;
    private LinearLayoutManager linearLayoutManager, linearLayoutManagerIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_activity);

        mainViewModel = new MainViewModel(this.getApplication());
        myAdapter = new MyAdapter(R.layout.item_column, this);  //This is because i implemented the interface in the activityclass
        incomeAdapter = new MyAdapter(R.layout.item_column, this);
        totalAmount = findViewById(R.id.kroner);
        recyclerView = findViewById(R.id.recyclerView_Income);
        upperImage = findViewById(R.id.upperImage);
        upperImage.setAlpha(127);  //deprecated but still works


        linearLayoutManagerIncome = new LinearLayoutManager(this);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);

        fab = findViewById(R.id.fab_income);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseIncomeOrExpenditure();
            }
        });
        observerSetup();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        mainViewModel.repository.dao.getTotalSum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                if (integer == null || integer == 0) {
                    totalAmount.setText("0");
                } else if (integer >= 0) {
                    totalAmount.setText("+ " + integer + " kr");
                } else {
                    totalAmount.setText(+integer + " kr");
                }

                editor.putString(getString(R.string.totalAmount_key), integer + "  kr");
                editor.commit();
            }
        });

        swapBetweenIncomeAndExpenditure();
    }

    private void swapBetweenIncomeAndExpenditure() {
        swapIncomeBtn = findViewById(R.id.swapIncome);
        swapExpenditureBtn = findViewById(R.id.swapExpenditure);

        swapIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncomeFragment incomeFragment = new IncomeFragment(mainViewModel, myAdapter);
                ReplaceIncomeList(incomeFragment);

                //observerSetup();
                //myAdapter.SetPlusOrMinus();
            }
        });

        swapExpenditureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenditureFragment expenditureFragment = new ExpenditureFragment(mainViewModel, myAdapter);
                ReplaceIncomeList(expenditureFragment);

            }
        });

        swapAllBtn = findViewById(R.id.swapAll);
        swapAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "clicked on ALL");
            }
        });
    }

    private void ReplaceIncomeList(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameHolder, fragment).commit();
    }

    private void chooseIncomeOrExpenditure() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.choose_sort_layout, null);
        dialog.setView(v);
        Log.d("inflating: ", v.toString());

        myDialog = dialog.create();
        myDialog.setCancelable(true);

        incomeBtn = v.findViewById(R.id.incomeBtn);
        expenditureBtn = v.findViewById(R.id.expenditureBtn);

        incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pressing: ", incomeBtn.toString());
                myDialog.dismiss();
                addNewIncomeItem();
            }
        });

        expenditureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add new Expenditure
                myDialog.dismiss();
                addNewExpenditureItem();
            }
        });

        myDialog.show();
    }

    //region Expenditure

    private void addNewExpenditureItem() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.add_expenditure_layout, null);
        dialog.setView(v);

        myDialog = dialog.create();
        myDialog.setCancelable(false);

        spinner = v.findViewById(R.id.spinnerItem);
        amount = v.findViewById(R.id.amountEdit);
        cancelBtn = v.findViewById(R.id.cancelBtn_2);
        saveBtn = v.findViewById(R.id.deleteBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budget = amount.getText().toString();
                myItem = spinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(budget)) {
                    amount.setError("You need to add an amount!");
                    return;
                }
                if (myItem.equals("Select Category ▽")) {
                    Toast.makeText(MyBudgetActivity.this, "You need to select a category!", Toast.LENGTH_SHORT).show();
                } else {
                    addExpenditureItemToDataBase();
                    myDialog.dismiss();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.show();
    }

    private void addExpenditureItemToDataBase() {

        DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = myDateFormat.format(calendar.getTime());

        MutableDateTime mutableDateTime = new MutableDateTime();
        mutableDateTime.setDate(0);
        DateTime thisMoment = new DateTime();
        Months months = Months.monthsBetween(mutableDateTime, thisMoment);

        itemClass = new Item(date, myItem, -Integer.parseInt(budget), months.getMonths());
        mainViewModel.insertItem(itemClass);
    }

    //endregion

    //region Income

    private void addNewIncomeItem() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.add_income_layout, null);
        dialog.setView(v);

        myDialog = dialog.create();
        myDialog.setCancelable(false);

        spinner = v.findViewById(R.id.spinnerItem);
        amount = v.findViewById(R.id.amountEdit);
        cancelBtn = v.findViewById(R.id.cancelBtn_2);
        saveBtn = v.findViewById(R.id.deleteBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budget = amount.getText().toString();
                myItem = spinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(budget)) {
                    amount.setError("You need to add an amount!");
                    return;
                }
                if (myItem.equals("Select Category ▽")) {
                    Toast.makeText(MyBudgetActivity.this, "You need to select a category!", Toast.LENGTH_SHORT).show();
                } else {
                    addIncomeItemToDataBase();
                    myDialog.dismiss();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.show();
    }

    private void addIncomeItemToDataBase() {

        DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = myDateFormat.format(calendar.getTime());

        MutableDateTime mutableDateTime = new MutableDateTime();
        mutableDateTime.setDate(0);
        DateTime thisMoment = new DateTime();
        Months months = Months.monthsBetween(mutableDateTime, thisMoment);

        itemClass = new Item(date, myItem, Integer.parseInt(budget), months.getMonths());
        mainViewModel.insertItem(itemClass);
    }

    //endregion

    private void deleteItemFromDatabase(int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.delete_layout, null);
        dialog.setView(v);

        myDialogDelete = dialog.create();
        myDialogDelete.setCancelable(false);

        deleteBtn = v.findViewById(R.id.incomeBtn);
        cancelBtn2 = v.findViewById(R.id.expenditureBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: DELETE ITEM FROM DATABASE
                List<Item> mItemList = mainViewModel.getAllItems().getValue();
                mainViewModel.deleteItem(mItemList.get(position));
                myDialogDelete.dismiss();
            }
        });

        cancelBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogDelete.dismiss();
            }
        });

        myDialogDelete.show();

        // THE FOLLOWING WORKS FOR FINDING AN ITEM
        /*int itemPosition = mainViewModel.loadUser(position);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: DELETE ITEM FROM DATABASE
                mainViewModel.loadUser(itemPosition);
                myDialogDelete.dismiss();
                Log.d("deleted: ", String.valueOf(itemPosition+1));
            }
        });*/

    }

    private void observerSetup() {
        mainViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                myAdapter.setItemList(items);
            }
        });
        mainViewModel.getSearchResult().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items.size() > 0) {
                    Log.d("id check", items.toString());
                } else {
                    Log.d("else id check", items.toString());
                }
            }
        });
    }

    /**
     * This is the implementation fo my interface I created in my Adapter class.
     */
    @Override
    public void onItemClick(int position) {
        idPosition = position;
        deleteItemFromDatabase(position);
    }
}
