package com.example.hezeyan.myapplication;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
import java.util.ListIterator;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Place> places;
    private listListener listener;
    private String listID;
    private String TAG = "ListAdapter: ";
    private Context context;
    public DatabaseReference databaseReference,listReference;
    public ListAdapter(final Context context, final ArrayList<Place> places,String listID){
        this.inflater = LayoutInflater.from(context);
        this.places = places;
        this.listID = listID;
        this.context = context;
    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final ListViewHolder holder = new ListViewHolder(inflater.inflate(R.layout.list_item,parent,false));
        return holder;
    }
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        final Place place = places.get(position);
        holder.name.setText(place.NameTurkish);
        holder.listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listReference = FirebaseUtil.getDBInstance().getReference(place.Key); // this will list the places in that list.
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(listener != null){
                    listener.onItemClick(place);
                }
            }
        }
        );
    }
    public void setLists(ArrayList<Place> places){
        this.places = places;
    }
    static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private LinearLayout listItem;
        public ListViewHolder (View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.list_item_name);
            listItem = itemView.findViewById(R.id.list_item_linearlayout);
        }
    }
    public void setListener(listListener listener){
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return places.size();
    }
    public interface listListener{
        void onItemClick(final Place place);
    }
}


