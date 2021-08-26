package com.example.pf.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoInterface {

    @Query("SELECT * FROM Item")
    List<Item> getAllUsers();

    /**
     * This works for finding an item but when calling it, you need to add +1 as it starts reading the array +1 offset.
     * */
    @Query("SELECT * FROM Item WHERE id IN (:userID)")
    List<Item> loadUserById(int userID);

    @Query("SELECT * FROM Item WHERE title IN (:title)")
    List<Item> findByTitle(String title);

    @Query("SELECT * FROM Item")
    LiveData<List<Item>> getAllTheIncomes();

    @Insert
    public void insertUser(Item income);

    @Update
    public void updateUser(Item income);

    @Query("DELETE FROM Item WHERE title=:title")
    public void delete(String title);

    @Query("DELETE FROM Item WHERE position =:itemPosition")
    public void deleteByClick(int itemPosition);

    @Delete
    public void deleteOne(Item item);

    @Query("SELECT amount, SUM(amount) as total FROM Item")
    int getTotalAmount();

    @Query("SELECT SUM(amount) FROM Item")
    LiveData<Integer> getTotalSum();
}
