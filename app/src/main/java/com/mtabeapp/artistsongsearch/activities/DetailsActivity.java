package com.mtabeapp.artistsongsearch.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mtabeapp.artistsongsearch.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {
    private String songName;
    private String artistName;
    private String collectionName;
    private String genre;
    private String previewURL;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String artwork = intent.getStringExtra("artwork");
        songName = intent.getStringExtra("songName");
        artistName = intent.getStringExtra("artistName");
        collectionName = intent.getStringExtra("collectionName");
        genre = intent.getStringExtra("genre");
        previewURL = intent.getStringExtra("previewURL");

        ImageView ivImage = findViewById(R.id.ivImage);
        TextView tvSongName = findViewById(R.id.tvSongName);
        TextView tvArtistName = findViewById(R.id.tvArtistName);
        TextView tvCollection = findViewById(R.id.tvCollectionName);
        TextView tvGenre = findViewById(R.id.tvGenre);

        Picasso.with(this).load(artwork)
                .placeholder(R.color.cardview_dark_background)
                .resize(100, 100)
                .into(ivImage);
        tvSongName.setText(songName);
        tvArtistName.setText(artistName);
        tvCollection.setText(collectionName);
        tvGenre.setText(genre);
    }

    //Called when share button pressed in activity_details.xml
    public void share(View view) {
        String subject = "Details of " + songName;
        String body = songName + "\n" +
                artistName + "\n" +
                collectionName + "\n" +
                genre + "\n\n" +
                previewURL;

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    //Called when play button pressed in activity_details.xml
    public void play(View view) {
        Uri uri = Uri.parse(previewURL);
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(this, uri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
    }
}
