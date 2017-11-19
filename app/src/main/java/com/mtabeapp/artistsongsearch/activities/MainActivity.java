package com.mtabeapp.artistsongsearch.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mtabeapp.artistsongsearch.R;
import com.mtabeapp.artistsongsearch.adapters.MainActivityAdapter;
import com.mtabeapp.artistsongsearch.helper.ConnectivityCheckHelper;
import com.mtabeapp.artistsongsearch.model.Song;
import com.mtabeapp.artistsongsearch.parser.SongJSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "ArtistSongSearch";
    private List<Song> mSongList;
    private RecyclerView recyclerView;
    private MainActivityAdapter adapter;

    private EditText etSearch;
    private View coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        etSearch = findViewById(R.id.etSearch);

        //Setting up recyclerView
        recyclerView = findViewById(R.id.rvMainActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //Called when search button pressed in activity_main.xml
    public void search(View view) {
        if(!etSearch.getText().toString().equals("")){
            if(ConnectivityCheckHelper.isOnline(getApplicationContext())) {
                requestData();
            } else {
                final Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.network_not_available,
                        5000);

                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search(view);
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }

            hideKeyboard(view);

        } else {
            //Called if no artist entered into editText
            Snackbar snackbar = Snackbar.make(view, R.string.enter_artist,
                    Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void hideKeyboard(View view) {
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void requestData() {
        //RequestParams are part of the Android Async Http Client Library
        RequestParams params = new RequestParams();
        String artistToSearch = etSearch.getText().toString();
        params.put("term", artistToSearch);

        String url = "https://itunes.apple.com/search?media=music";

        //AsyncHttpClient is from the Android Async HTTP client library
        //This is where the HTTP request is made
        AsyncHttpClient client = new AsyncHttpClient(0);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "Success! JSON: " + response.toString());
                try {
                    String result = response.getString("results");
                    int resultCount = response.getInt("resultCount");

                    if(resultCount == 0){
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.no_results,
                                Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    mSongList = SongJSONParser.parseFeed(result);

                    if(mSongList != null) {
                        adapter = new MainActivityAdapter(mSongList, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    } else {
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.parsing_error,
                                Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG, "Fail " + throwable.toString());
                Log.d(TAG, "Status code " + statusCode);
                Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.request_failed,
                        Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }

    //Called when clear button pressed in activity_main.xml
    public void clear(View view) {
        etSearch.setText("");
        recyclerView.setVisibility(View.INVISIBLE);
    }
}
