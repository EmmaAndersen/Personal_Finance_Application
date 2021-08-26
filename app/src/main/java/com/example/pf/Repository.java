package com.example.pf;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pf.Database.DaoInterface;
import com.example.pf.Database.Item;
import com.example.pf.Database.MyDatabase;

import java.util.List;

public class Repository {

    private MutableLiveData<List<Item>> itemsResult = new MutableLiveData<List<Item>>();
    public DaoInterface dao;
    private LiveData<List<Item>> allItems;
    private int tempSum;

    public Repository(Application app){
        MyDatabase database;
        database = MyDatabase.getInstance(app);
        dao = database.dao();
        allItems = dao.getAllTheIncomes();

    }

    public LiveData<List<Item>> getAllItems(){
        return allItems;
    }

    public MutableLiveData<List<Item>> getSearchResults(){
        return itemsResult;
    }

    public void insertItem(Item item){
        InsertAsyncTask task = new InsertAsyncTask(dao);
        task.execute(item);
    }

    public void deleteItem(Item item){
        DeleteAsyncTask task = new DeleteAsyncTask(dao);
        task.execute(item);
    }

    public LiveData<Integer> sumAllItems(){
        /*GetTotalAsyncTask task = new GetTotalAsyncTask(dao);
        task.execute();
        tempSum = task.sumValue;
        Log.d("mySum", String.valueOf(tempSum));
        return tempSum;*/

        return dao.getTotalSum();
    }

   /* public Integer getTotalSum(){
        return dao.getTotalSum();
    }*/

    /**
     * Delete selected item from the recyclerview
     * */
    public void deleteItemWithClick(int position){
        DeleteAsyncTaskClick task = new DeleteAsyncTaskClick(dao);
        task.execute(position);
    }

    public void findItem(int title){
        QueryAsyncTask task = new QueryAsyncTask(dao);
        task.execute(title);
    }

    private static class InsertAsyncTask extends AsyncTask<Item,
            Void, List<Item>> {
        private DaoInterface asyncTaskDao;
        InsertAsyncTask(DaoInterface dao){
            asyncTaskDao=dao;
        }
        @Override
        protected List<Item> doInBackground(Item... params){
            asyncTaskDao.insertUser(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Item, Void,
            Void>{
        private DaoInterface asyncTaskDao;
        DeleteAsyncTask(DaoInterface dao){
            asyncTaskDao=dao;
        }
        @Override
        protected Void doInBackground(Item... params){
            asyncTaskDao.deleteOne(params[0]);
            return null;
        }
    }

    /**
     * Selected item
     * */
    private static class DeleteAsyncTaskClick extends AsyncTask<Integer, Void,
            Void>{
        private DaoInterface asyncTaskDao;
        DeleteAsyncTaskClick(DaoInterface dao){
            asyncTaskDao=dao;
        }
        @Override
        protected Void doInBackground(Integer... params){
            asyncTaskDao.deleteByClick(params[0]);
            return null;
        }
    }

    private static class QueryAsyncTask extends AsyncTask<Integer, Void, List<Item>> {

        private DaoInterface asyncTaskDao;
        private Repository delegate=null;
        QueryAsyncTask(DaoInterface dao){
            asyncTaskDao = dao;
        }

        @Override
        protected List<Item> doInBackground(Integer... params) {
            return asyncTaskDao.loadUserById(params[0]);
        }
    }

    private static class GetTotalAsyncTask extends AsyncTask<Void, Void,
            Integer>{

        private DaoInterface asyncTaskDao;
        private int sumValue;

        private GetTotalAsyncTask(DaoInterface noteDao) {
            this.asyncTaskDao = noteDao;
        }
        @Override
        protected Integer doInBackground(Void... params) {
            return null;
        }
    }

}
