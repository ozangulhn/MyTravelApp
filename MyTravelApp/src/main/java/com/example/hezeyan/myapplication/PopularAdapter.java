package com.example.hezeyan.myapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private LayoutInflater inflater;
    private List<ListObj> lists;
    private PopularListener listener;
    private DatabaseReference databaseReference;
    private String TAG = "PopularAdapter: ";
    private Context context;
    public PopularAdapter(final Context context, final List<ListObj> lists) {
        this.inflater = LayoutInflater.from(context);
        this.lists = lists;
        this.context = context;
    }

    @Override
    public PopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final PopularViewHolder holder = new PopularViewHolder(inflater.inflate(R.layout.popular_item, parent, false));
        return holder;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public void onBindViewHolder(PopularViewHolder holder, int position) {
        final ListObj list = lists.get(position);
        holder.name.setText(list.getName());
        holder.itemLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: item clicked " + list.getId());
                ((PopularActivity)context).goToLists(list);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(list);
                }
            }
        });
    }

    public void setPlaces(ArrayList<ListObj> places) {
        this.lists = places;
    }

    public void setListener(PopularListener listener) {
        this.listener = listener;
    }

    static class PopularViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private LinearLayout itemLinear;
        public PopularViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.popularitem_name);
            itemLinear = itemView.findViewById(R.id.popularitem_linear);
        }
    }

    public interface PopularListener{
        void onItemClick(final ListObj list);
    }


}
