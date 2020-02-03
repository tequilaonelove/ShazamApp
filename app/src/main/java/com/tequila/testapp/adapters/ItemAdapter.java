package com.tequila.testapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.tequila.testapp.R;
import com.tequila.testapp.models.ItemList;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ItemList> arrayList;

    public ItemAdapter(Context context, ArrayList<ItemList> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final ItemList itemList = arrayList.get(position);
        final int pos = position + 1;
        holder.artist.setText(itemList.getArtist());
        holder.title.setText(itemList.getTitle());
        holder.tvCount.setText(String.valueOf(pos));

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE).borderWidthDp(1).cornerRadiusDp(5)
                .oval(false).build();

        Picasso.get().load(itemList.getCoverUrl())
                .centerCrop().fit()
                .transform(transformation)
                .into(holder.coverImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "item: " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView coverImage;
        TextView artist;
        TextView title;
        TextView tvCount;

        MyViewHolder(View view) {
            super(view);
            artist = view.findViewById(R.id.artist);
            title = view.findViewById(R.id.title);
            coverImage = view.findViewById(R.id.cover);
            tvCount = view.findViewById(R.id.tv_countTop);
        }
    }

}