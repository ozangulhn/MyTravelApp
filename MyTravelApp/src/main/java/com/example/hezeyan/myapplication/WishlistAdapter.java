package com.example.hezeyan.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

    private LayoutInflater inflater;
    private List<Place> places;
    private WishlistListener listener;
    private DatabaseReference databaseReference;
    public WishlistAdapter(final Context context, final List<Place> places) {
        this.inflater = LayoutInflater.from(context);
        this.places = places;
    }

    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final WishlistViewHolder holder = new WishlistViewHolder(inflater.inflate(R.layout.wishlist_item, parent, false));
        return holder;
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    @Override
    public void onBindViewHolder(WishlistViewHolder holder, int position) {
        final Place place = places.get(position);
        holder.name.setText(place.NameTurkish);
        holder.deleteFromWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseUtil.getDBInstance().getReference("Wishlist/"+place.NameTurkish);
                databaseReference.removeValue();
            }
        });

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

    public void setListener(WishlistListener listener) {
        this.listener = listener;
    }

    static class WishlistViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageButton deleteFromWishlistButton;
        public WishlistViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.wishlistitem_name);
            deleteFromWishlistButton = itemView.findViewById(R.id.deleteFromWishlistButton);
        }
    }

    public interface WishlistListener{
        void onItemClick(final Place place);
    }


}
