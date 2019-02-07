package com.example.hezeyan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private LayoutInflater inflater;
    private List<Place> places;
    private SearchListener listener;
    private DatabaseReference databaseReference;
    private Context context;
    private String TAG = "SearchAdapter";
    public SearchAdapter(final Context context, final List<Place> places) {
        this.inflater = LayoutInflater.from(context);
        this.places = places;
        this.context = context;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final SearchViewHolder holder = new SearchViewHolder(inflater.inflate(R.layout.item_search, parent, false));
        return holder;
    }
    @Override
    public int getItemCount() {
        return places.size();
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        final Place place = places.get(position);
        holder.name.setText(place.NameTurkish);
        holder.info.setText(place.InformationTurkish);
        holder.AddToChecklistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {  //USE THIS FOR ADDING TO A LIST
                databaseReference = FirebaseUtil.getDBInstance().getReference("Checklist/"+place.NameTurkish);
                Log.d(TAG, "onClick: " + databaseReference.toString());
                databaseReference.setValue(place);
                ((SearchActivity) context).onClickCalled(place);
            }
        });

        holder.AddToWishlistButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "onClick: item clicked with place" + place.NameTurkish);
                ((SearchActivity) context).onPlaceCalled(place);
                //context.startActivity(new Intent(context,PlaceDetailsActivity.class));
                //databaseReference = FirebaseUtil.getDBInstance().getReference("Wishlist/"+place.NameTurkish);
                //databaseReference.setValue(place);
            }});

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(place);
                }
            }
        });
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public void setListener(SearchListener listener) {
        this.listener = listener;
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView info;
        private ImageButton AddToChecklistButton,AddToWishlistButton;
        public SearchViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.search_name);
            info = itemView.findViewById(R.id.search_info);
            AddToChecklistButton = itemView.findViewById(R.id.checklistButton_inSearch);
            AddToWishlistButton = itemView.findViewById(R.id.wishlistButton_inSearch);
        }
    }

    public interface SearchListener{
        void onItemClick(final Place place);
    }
}
