package com.example.hezeyan.myapplication;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder> {
    //private FirebaseAuth auth;
    private LayoutInflater inflater;
    private List<Place> places;
    private ChecklistListener listener;
    private DatabaseReference databaseReference;
    public ChecklistAdapter(final Context context, final List<Place> places) {
        this.inflater = LayoutInflater.from(context);
        this.places = places;
    }

    @Override
    public ChecklistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ChecklistViewHolder holder = new ChecklistViewHolder(inflater.inflate(R.layout.checklist_item, parent, false));
        return holder;
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    @Override
    public void onBindViewHolder(ChecklistViewHolder holder, int position) {
        final Place place = places.get(position);
        holder.name.setText(place.NameTurkish);
        holder.deleteFromChecklistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseUtil.getDBInstance().getReference("Checklist/"+place.NameTurkish);
                databaseReference.removeValue();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!= null){
                    listener.onItemClick(place);
                }
            }
        });
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public void setListener(ChecklistListener listener) {
        this.listener = listener;
    }

    static class ChecklistViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageButton deleteFromChecklistButton;
        public ChecklistViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.checklistitem_name);
            deleteFromChecklistButton = itemView.findViewById((R.id.deleteFromChecklistButton));
        }
    }

    public interface ChecklistListener{
        void onItemClick(final Place place);
    }
}
