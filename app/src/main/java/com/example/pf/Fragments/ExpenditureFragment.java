package com.example.pf.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.pf.Database.Item;
import com.example.pf.MainViewModel;
import com.example.pf.MyAdapter;
import com.example.pf.R;

import java.util.ArrayList;
import java.util.List;

public class ExpenditureFragment extends Fragment implements MyAdapter.onMyItemClickListener {

    RecyclerView recyclerView;
    private int idPosition;

    private MyAdapter myAdapter;
    public MainViewModel mainViewModel;

    private List<Item> incomeItems;

    public ExpenditureFragment(MainViewModel mvm, MyAdapter myAdapter) {
        this.mainViewModel = mvm;
        this.myAdapter = myAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_IncomeFragment);
        if (recyclerView.getLayoutManager() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(myAdapter);
        }
        observerSetup();

       // incomeItems = mainViewModel.getAllItems().getValue();
        //for (int i = 0; i < myAdapter.getItemCount(); i++) {
          //  if (mainViewModel.getAllItems().getValue().get(i).amount >= 0) {
            //    incomeItems.remove(mainViewModel.getAllItems().getValue().get(i));
              //  //myAdapter.setItemList(incomeItems);
            //}
       // }


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
                incomeItems  = new ArrayList<>();  //items
                for (int i = 0; i < mainViewModel.getAllItems().getValue().size(); i++) {
                    if (items.get(i).amount < 0) {
                        incomeItems.add(items.get(i));
                        myAdapter.setItemList(incomeItems);
                    }
                }
                myAdapter.notifyDataSetChanged();
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