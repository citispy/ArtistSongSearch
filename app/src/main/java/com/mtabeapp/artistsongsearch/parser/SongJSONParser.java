package com.mtabeapp.artistsongsearch.parser;

import com.mtabeapp.artistsongsearch.model.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongJSONParser {
    private static List<Song> songList;

    public static List<Song> parseFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            songList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Song song = new Song();

                //Parsing ID's
                song.setSongName(obj.getString("trackName"));
                song.setArtistName(obj.getString("artistName"));
                song.setArtWork(obj.getString("artworkUrl100"));
                song.setCollectionName(obj.getString("collectionName"));
                song.setGenre(obj.getString("primaryGenreName"));
                song.setPreviewURL(obj.getString("previewUrl"));

                songList.add(song);
            }
            return songList;
        } catch (JSONException e) {
            e.printStackTrace();
            return songList;
        }

    }

}
