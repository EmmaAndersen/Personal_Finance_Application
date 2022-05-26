package com.example.pf.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.pf.Database.Item;
import com.example.pf.MainViewModel;
import com.example.pf.MyAdapter;
import com.example.pf.R;

import java.util.Collection;
import java.util.List;

public class IncomeFragment extends Fragment implements MyAdapter.onMyItemClickListener{

    RecyclerView recyclerView;
    private int idPosition;

    private MyAdapter myAdapter;
    public MainViewModel mainViewModel;

    public IncomeFragment(MainViewModel mvm, MyAdapter myAdapter) {
        this.mainViewModel = mvm;
        this.myAdapter = myAdapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_IncomeFragment);
        if(recyclerView.getLayoutManager() == null){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(myAdapter);
        }

        observerSetup();

        return view;
    }

    @Override
    public void onItemClick(int position) {
        idPosition = position;
    }

    private void observerSetup() {
        mainViewModel.getAllItems().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                for(int i = 0; i < myAdapter.getItemCount(); i++){
                    if(items.get(i).amount >= 0){
                        List<Item> incomeItems = items;
                        myAdapter.setItemList(incomeItems);
                        Log.d("ITEMTAG", "items: " + incomeItems.get(i).amount);
                    }
                }
            }
        });
        mainViewModel.getSearchResult().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {  //getViewLifecycleOwner() to this ?
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

}
