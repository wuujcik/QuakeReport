package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    /**Tag for log messages*/
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /**Query URL */
    private String mUrl;


    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        // if there's no url or first url is null, return early with null
        if (mUrl == null) {
            return null;
        } else {
            // Perform the HTTP request for earthquake data and process the response.
            List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
            return earthquakes;
        }
    }
}