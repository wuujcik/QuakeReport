/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    // Adapter for the list of earthquakes
    private EarthquakeAdapter mAdapter;

    //URL for earthquake data from the USGS dataset
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


    //test for emptyView
    //private static final String USGS_REQUEST_URL = "";

    //Constant value for the earthquake loader ID.
    private static final int EARTHQUAKE_LOADER_ID = 1;

    //ListView for the list of earthquakes
    private ListView earthquakeListView;
    //TextView that is displayed when the list is empty
    private TextView mEmptyStateTextView;
    //ProgressBar shows progress of loading data
    private ProgressBar mProgressBar;

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new EarthquakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        //sets the ProgressBar to gone when data is loaded
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        //if there's no valid list of Earthquakes, shows empty state text that informs of no earthquakes found
        mEmptyStateTextView.setText(R.string.no_earthquakes_found);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of Earthquakes, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        mAdapter.clear();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Get a reference to the ListView, and attach the adapter to the listView.
        earthquakeListView = findViewById(R.id.list);
        earthquakeListView.setAdapter(mAdapter);

        //Check if there is internet connection
        boolean connectedToInternet = checkConnectionStatus();
        if (!connectedToInternet){
            //sets the ProgressBar to gone if no internet
            mProgressBar = findViewById(R.id.progress_bar);
            mProgressBar.setVisibility(View.GONE);

            //inform user of lack of internet connection
            Log.v("test: ", "weszlo");
            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            earthquakeListView.setEmptyView(mEmptyStateTextView);
            mEmptyStateTextView.setText(R.string.no_internet);
        } else {

        //When a list item is clicked, the website with more details about the earthquake is opened
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Earthquake currentEarthquake = mAdapter.getItem(position);

                if (currentEarthquake.getmWeb() != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(currentEarthquake.getmWeb()));
                    startActivity(intent);
                }

            }
        });

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
    }}

    private boolean checkConnectionStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
         boolean isConnected = activeNetwork !=null && activeNetwork.isConnected();
         return isConnected;
    }


}
