package com.example.smd_assignment_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestrauntListAdapter extends RecyclerView.Adapter<RestrauntListAdapter.ViewHolder>{

    private ArrayList<Restraunt> restrauntslist;
    public RestrauntListAdapter(Context context, ArrayList<Restraunt> restrauntslist)
    {
        this.restrauntslist = restrauntslist;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ratings, name, location, phoneno, description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratings = itemView.findViewById(R.id.tvRatings);
            name = itemView.findViewById(R.id.tvRestrauntName);
            location = itemView.findViewById(R.id.tvLocation);
            phoneno = itemView.findViewById(R.id.tvPhoneNumber);
            description = itemView.findViewById(R.id.tvDescription);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(view);
    }

    public void filterList (ArrayList<Restraunt> filteredList) {
        this.restrauntslist = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setTag(restrauntslist.get(position));

        holder.ratings.setText(restrauntslist.get(position).getRating()+"");
        holder.name.setText(restrauntslist.get(position).getName());
        holder.location.setText(restrauntslist.get(position).getLocation());
        holder.phoneno.setText(restrauntslist.get(position).getPhone());
        holder.description.setText(restrauntslist.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return restrauntslist.size();
    }
}