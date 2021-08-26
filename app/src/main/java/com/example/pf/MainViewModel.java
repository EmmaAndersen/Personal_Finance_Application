package com.example.pf;

import android.app.Application;
import android.renderscript.Sampler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pf.Database.Item;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public Repository repository;
    private LiveData<List<Item>> allItems;
    private MutableLiveData<List<Item>> searchResult;
    private int itemID;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allItems = repository.getAllItems();
        searchResult = repository.getSearchResults();
    }

    public MutableLiveData<List<Item>> getSearchResult() {
        return searchResult;
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public void insertItem(Item item) {
        repository.insertItem(item);
    }

    public void deleteItem(Item item) {
        repository.deleteItem(item);
    }

    public LiveData<Integer> sumAllItems() {
        return repository.sumAllItems();
    }


   /* public void findItem(String title){
        repository.findItem(title);
    }*/

    public int loadUser(int id) {
        itemID = id;
        repository.findItem(itemID);
        return itemID;
    }

    public void deleteOneItem(int id) {
        itemID = id;
        repository.deleteItemWithClick(itemID);
    }
}
