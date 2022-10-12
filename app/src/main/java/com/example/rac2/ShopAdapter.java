package com.example.rac2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    Context context;
    ArrayList<Shop> shopArrayList;

    public ShopAdapter(Context context, ArrayList<Shop> shopArrayList) {
        this.context = context;
        this.shopArrayList = shopArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.rcv, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shop shop = shopArrayList.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.soLuong.setText(String.valueOf(shop.getSoLuong()));
        holder.gia.setText(decimalFormat.format(shop.getGia()) + " đ");

        holder.setOnItemClickListener((view, position1, isClick) -> {
            if (!isClick) {
                Log.d("sssssss", shop.getSoLuong() + "");
                Toast.makeText(context, "Bạn đã mua thành công " + shop.getSoLuong()
                        + " với giá là " + decimalFormat.format(shop.getGia()) + " đ", Toast.LENGTH_SHORT).show();
                MainActivity.dialog.dismiss();
                MainActivity.tView.setText((MainActivity.score += shop.getSoLuong()) + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("ddd", shopArrayList.size() + "");
        return shopArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView soLuong, gia;
        OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            soLuong = itemView.findViewById(R.id.soLuongRcv);
            gia = itemView.findViewById(R.id.giaRcv);

            itemView.setOnClickListener(v -> {
                onItemClickListener.setOnItemClickListener(itemView, getAdapterPosition(), false);
            });
        }
    }
}
