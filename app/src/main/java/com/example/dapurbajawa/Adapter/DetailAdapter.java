package com.example.dapurbajawa.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dapurbajawa.Model.DetailModel;
import com.example.dapurbajawa.Model.ProdukModel;
import com.example.dapurbajawa.R;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder>{

    private ArrayList<DetailModel> dataList;
    private ArrayList<ProdukModel> mProdukList;
    View viewku;

    public DetailAdapter(ArrayList<DetailModel> dataList, ArrayList<ProdukModel> mProdukList) {
        this.dataList = dataList;
        this.mProdukList = mProdukList;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.detail_item, parent, false);
        return new DetailAdapter.DetailViewHolder(viewku);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        for (int i = 0; i < mProdukList.size(); i++) {
            if (dataList.get(position).getKodeMakanan().equals(mProdukList.get(i).getKodeMakanan())){
                holder.tvNamaitem.setText(mProdukList.get(i).getNamaMakanan());
            }
        }
        holder.tvJumlahitem.setText(dataList.get(position).getJumlah());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaitem, tvJumlahitem;

        DetailViewHolder(View itemView) {
            super(itemView);
            tvNamaitem =itemView.findViewById(R.id.tvNamaitem);
            tvJumlahitem =itemView.findViewById(R.id.tvJumlahitem);
        }
    }

}
