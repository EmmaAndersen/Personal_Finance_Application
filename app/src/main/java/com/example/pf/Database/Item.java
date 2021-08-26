package com.example.pf.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.pf.MainActivity;

/*Entity represents a table within the database. All the fields in user,
corresponds to columns/in the table. i.e this class should not have any logic.*/

@Entity/*(tableName = MainActivity.TABLE_NAME)*/
public class Item {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "date")
    public String Date;

    @ColumnInfo(name = "title")
    public String Title;

    @ColumnInfo(name = "category")
    public String Category;

    @ColumnInfo(name = "Amount")
    public int amount;

    @ColumnInfo(name = "months")
    public int Months;

    @ColumnInfo(name = "position")
    public int Position;

    public Item(int id, String Date/*, String Title*/, String Category, int amount) {
        this.id = id;
        this.Date = Date;
        /*this.Title = Title;*/
        this.Category = Category;
        this.amount = amount;
    }

    @Ignore
    public Item(String Date /*, String Title*/, String Category, int amount, int Months) {
        this.Date = Date;
        //this.Title = Title;
        this.Category = Category;
        this.amount = amount;
        this.Months = Months;
    }

    @Ignore
    public Item(){
        id = getId();
        Date = getDate();
        Category = getCategory();
        amount = getAmount();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String first_name) {
        this.Date = Date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String amount) {
        this.Title = Title;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
