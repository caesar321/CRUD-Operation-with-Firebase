package com.example.crudoperationwithfirebase3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myAdapter extends  RecyclerView.Adapter<myAdapter.ViewHolder>{
    private Context context;
    private ArrayList<User>myUser;

    public myAdapter(Context context, ArrayList<User> myUser) {
        this.context = context;
        this.myUser = myUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.topic.setText(myUser.get(position).getTitle());
        holder.datee.setText(myUser.get(position).getDate());
        holder.description.setText(myUser.get(position).getDescription());
        Picasso.get().load(myUser.get(position).getImageUri()).into(holder.shapedImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,DetailActivity.class);
                intent.putExtra("Topic",myUser.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("Date",myUser.get(holder.getAdapterPosition()).getDate());
                intent.putExtra("Des",myUser.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("Image",myUser.get(holder.getAdapterPosition()).getImageUri());
                intent.putExtra("key",myUser.get(holder.getAdapterPosition()).getKey());
                // intent.putExtra("key",myModal.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myUser.size();
    }
    public void searchmyModalClass(ArrayList<User>searchList){
        myUser=searchList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView shapedImage;
        private TextView topic,datee,description;
        private MaterialCardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shapedImage= itemView.findViewById(R.id.shapeImage);
            topic= itemView.findViewById(R.id.txtTitle);
            datee= itemView.findViewById(R.id.txtDate);
            shapedImage= itemView.findViewById(R.id.shapeImage);
            cardView= itemView.findViewById(R.id.cardView);
            description= itemView.findViewById(R.id.txtdesc);
        }
    }
}
