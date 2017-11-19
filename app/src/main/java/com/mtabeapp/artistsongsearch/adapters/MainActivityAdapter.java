package com.mtabeapp.artistsongsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mtabeapp.artistsongsearch.R;
import com.mtabeapp.artistsongsearch.activities.DetailsActivity;
import com.mtabeapp.artistsongsearch.model.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.AdapterViewHolder>{
    private List<Song> mSongList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MainActivityAdapter(List<Song> songList, Context context){
        mSongList = songList;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }
    @Override
    public MainActivityAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.card_view_main_activity, parent, false);
        return new AdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainActivityAdapter.AdapterViewHolder holder, int position) {
        Song song = mSongList.get(position);

        final String artwork = song.getArtWork();
        final String songName = song.getSongName();
        final String artistName = song.getArtistName();
        final String collectionName = song.getCollectionName();
        final String genre = song.getGenre();
        final String previewURL = song.getPreviewURL();

        //Loading artwork into imageView with Picasso
        Picasso.with(mContext).load(artwork)
                .placeholder(R.color.cardview_dark_background)
                .resize(100, 100)
                .into(holder.ivImage);

        holder.tvSongName.setText(songName);
        holder.tvArtistName.setText(artistName);

        holder.cvMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsActivity.class);

                intent.putExtra("artwork", artwork);
                intent.putExtra("songName", songName);
                intent.putExtra("artistName", artistName);
                intent.putExtra("collectionName", collectionName);
                intent.putExtra("genre", genre);
                intent.putExtra("previewURL", previewURL);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    static class AdapterViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivImage;
        private TextView tvSongName;
        private TextView tvArtistName;
        private CardView cvMainActivity;

        private AdapterViewHolder(View v) {
            super(v);
            ivImage = v.findViewById(R.id.ivImage);
            tvSongName = v.findViewById(R.id.tvSongName);
            tvArtistName = v.findViewById(R.id.tvArtistName);
            cvMainActivity = v.findViewById(R.id.cvMainActivity);
        }
    }
}
