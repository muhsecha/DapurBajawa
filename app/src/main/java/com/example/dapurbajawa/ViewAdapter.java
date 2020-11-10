package com.example.dapurbajawa;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewViewHolder> {

    private ArrayList<HomeModel> dataList;
    View viewku;

    public ViewAdapter(ArrayList<HomeModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewViewHolder extends RecyclerView.ViewHolder {
        public ViewViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView txtnama,txtnotelp;


        }
    }
}
