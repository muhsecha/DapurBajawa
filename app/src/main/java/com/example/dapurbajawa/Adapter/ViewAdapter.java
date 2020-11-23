package com.example.dapurbajawa.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dapurbajawa.DetailDataActivity;
import com.example.dapurbajawa.Model.HomeModel;
import com.example.dapurbajawa.R;

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
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewViewHolder(viewku);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewViewHolder holder, final int position) {
        holder.tvNama.setText(dataList.get(position).getNama());
        holder.tvTlp.setText(dataList.get(position).getNohp());
        holder.tvWaktu.setText(dataList.get(position).getTanggal());
        holder.cvInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailDataActivity.class);
                intent.putExtra("id", dataList.get(position).getId());
                intent.putExtra("nama", dataList.get(position).getNama());
                intent.putExtra("nohp", dataList.get(position).getNohp());
                intent.putExtra("note", dataList.get(position).getNote());
                intent.putExtra("kode", dataList.get(position).getKode());
                intent.putExtra("status", dataList.get(position).getStatus());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvTlp, tvWaktu;
        CardView cvInbox;

        ViewViewHolder(View itemView) {
            super(itemView);
            tvNama =itemView.findViewById(R.id.tvNama);
            tvTlp =itemView.findViewById(R.id.tvnotlp);
            tvWaktu =itemView.findViewById(R.id.tvwaktu);
            cvInbox =itemView.findViewById(R.id.cdPesan);

        }
    }
}
