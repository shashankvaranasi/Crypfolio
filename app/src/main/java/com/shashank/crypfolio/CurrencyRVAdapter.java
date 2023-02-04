package com.shashank.crypfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.CurrencyViewholder> {
    private static final DecimalFormat df2 = new DecimalFormat("#.##");
    private ArrayList<CurrencyModel> currencyModels;
    private final Context context;

    public CurrencyRVAdapter(ArrayList<CurrencyModel> currencyModels, Context context) {
        this.currencyModels = currencyModels;
        this.context = context;
    }

    public void filterList(ArrayList<CurrencyModel> filterlist) {
        currencyModels = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CurrencyRVAdapter.CurrencyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false);
        return new CurrencyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyRVAdapter.CurrencyViewholder holder, int position) {
        CurrencyModel model = currencyModels.get(position);
        holder.nameTV.setText(model.getName());
        holder.rateTV.setText("$ " + df2.format(model.getPrice()));
        holder.symbolTV.setText(model.getSymbol());
        holder.rankTV.setText(model.getRank());

        Picasso.get()
                .load(String.format("https://s2.coinmarketcap.com/static/img/coins/64x64/%s.png", model.getId()))
                .into(holder.iconTV);
    }

    @Override
    public int getItemCount() {
        return currencyModels.size();
    }

    public static class CurrencyViewholder extends RecyclerView.ViewHolder {
        private final TextView symbolTV, rateTV, nameTV, rankTV;
        private final ImageView iconTV;

        public CurrencyViewholder(@NonNull View itemView) {
            super(itemView);
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTV = itemView.findViewById(R.id.idTVRate);
            nameTV = itemView.findViewById(R.id.idTVName);
            rankTV = itemView.findViewById(R.id.idTVRank);
            iconTV = itemView.findViewById((R.id.idIVIcon));

        }
    }
}