package com.example.pf;

import android.content.res.Resources;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pf.Activities.MyBudgetActivity;
import com.example.pf.Database.Item;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private int itemCount;
    private List<Item> itemList;
    private onMyItemClickListener onMyItemClickListener;

    public String typeOfFinance;
    public int typeIndex;
    private ViewHolder myViewHolder;

    public MyAdapter(int id, onMyItemClickListener onMyItemClickListener) {
        itemCount = id;
        this.onMyItemClickListener = onMyItemClickListener;
        typeIndex = 0;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemCount, parent, false);
        myViewHolder = new ViewHolder(v, onMyItemClickListener);
        return myViewHolder;
    }

    public void setItemList(List<Item> items) {
        itemList = items;
        notifyDataSetChanged();
    }

    public void SetPlusOrMinus(){
        for (int i = 0; i < itemList.size(); i++){
            if(itemList.get(i).getAmount() >= 0){
                Log.d("CHECKTAG", "item more than zero: " + itemList.get(i).amount);

            }
        }
    }

   /* @Override
    public int getItemViewType(int position) {
        if(itemList.get(position).amount >= 0){
            //typeOfFinance = "INCOME";
            if(itemList != null && myViewHolder != null){
                for(int i = 0; i < itemList.size(); i++){
                    myViewHolder.amount.setVisibility(View.INVISIBLE);
                    myViewHolder.itemImage.setVisibility(View.INVISIBLE);
                    myViewHolder.date.setVisibility(View.INVISIBLE);

                }
            }
        } else{
            typeOfFinance = "EXPENDITURE";

        }
        Log.d("TYPETAG", ": " + typeOfFinance);
        return position;
    }*/

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(itemList.get(position));

        TextView itemCategory = holder.category;
        itemCategory.setText(itemList.get(position).getCategory());

        switch (itemCategory.getText().toString()) {
            case "Food":
                holder.itemImage.setImageResource(R.mipmap.foodimg_foreground);
                break;
            case "House":
                holder.itemImage.setImageResource(R.mipmap.houseimg_foreground);
                break;
            case "Charity":
                holder.itemImage.setImageResource(R.mipmap.charityimg_foreground);
                break;
            case "Cats":
                holder.itemImage.setImageResource(R.mipmap.catimg_foreground);
                break;
            case "Education":
                holder.itemImage.setImageResource(R.mipmap.educationimg_foreground);
                break;
            case "Beer":
                holder.itemImage.setImageResource(R.mipmap.beerimg_foreground);
                break;
            case "Treat yourself":
                holder.itemImage.setImageResource(R.mipmap.starimg_foreground);
                break;
            case "Other":
                holder.itemImage.setImageResource(R.mipmap.otherimg_foreground);
                break;
            case "Gift":
                holder.itemImage.setImageResource(R.mipmap.giftimg_foreground);
                break;
            case "Salary":
                holder.itemImage.setImageResource(R.mipmap.salaryimg_foreground);
                break;
        }

        TextView itemAmount = holder.amount;
        itemAmount.setText("" + itemList.get(position).getAmount() + "  kr");

        TextView itemDate = holder.date;
        itemDate.setText(itemList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView category, amount, date;
        ImageView itemImage;
        onMyItemClickListener myOnItemClickListener;
        int ID;

        public ViewHolder(@NonNull View itemView, onMyItemClickListener onMyItemClickListener) {
            super(itemView);
            //title = itemView.findViewById(R.id.titleTxt);
            itemImage = itemView.findViewById(R.id.imageID);
            category = itemView.findViewById(R.id.tv_category);
            amount = itemView.findViewById(R.id.tv_amount);
            date = itemView.findViewById(R.id.tv_date);
            this.myOnItemClickListener = onMyItemClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            myOnItemClickListener.onItemClick(getAdapterPosition());
            ID = itemList.get(getAdapterPosition()).getId();
        }
    }

    public class ViewHolderPositive extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView category, amount, date;
        ImageView itemImage;
        onMyItemClickListener myOnItemClickListener;
        int ID;

        public ViewHolderPositive(@NonNull View itemView, onMyItemClickListener onMyItemClickListener) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.imageID);
            category = itemView.findViewById(R.id.tv_category);
            amount = itemView.findViewById(R.id.tv_amount);
            date = itemView.findViewById(R.id.tv_date);
            this.myOnItemClickListener = onMyItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMyItemClickListener.onItemClick(getAdapterPosition());
            ID = itemList.get(getAdapterPosition()).getId();
        }
    }

    public interface onMyItemClickListener {
        void onItemClick(int position);
    }
}