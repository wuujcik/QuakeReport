package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Earthquake currentQuake = getItem(position);


        /** This part of adapter takes care of the magnitude*/

        double currentMag = currentQuake.getmMagnitude();
        DecimalFormat magFormatter = new DecimalFormat("0.0");
        String magnitudeToDisplay = magFormatter.format(currentMag);

        // Find the TextView in the list_item.xml layout with the ID element_magnitude
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.element_magnitude);
        // Display the magnitude of the current earthquake in that TextView
        magnitudeView.setText(magnitudeToDisplay);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentQuake.getmMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);



        /** This part of adapter takes care of the location*/

        String currentLocation = currentQuake.getmLocation();

        String distance;
        String city;

        //if the location is specific (and contains "of"), it will be splited into two parts
        //first (distance) - the distance to the city
        //second (city) - the city itself
        if (currentLocation.contains("of")) {
            int index = currentLocation.indexOf("of");
            distance = currentLocation.substring(0, (index + 2));
            city = currentLocation.substring((index + 3), (currentLocation.length()+0));
        } else {
            //if the location is general there will be added text "near the"
            //to the first String (distance)
            //and full location will be set to the second String (city)
            distance = getContext().getString(R.string.near_the);
            city = currentLocation;
        }


        //  Find the TextView in the list_item.xml layout with the ID element_distance
        TextView distanceView = (TextView) listItemView.findViewById(R.id.element_distance);
        /// Display the location of the current earthquake in that TextView
        distanceView.setText(distance);

        //  Find the TextView in the list_item.xml layout with the ID element_city
        TextView locationView = (TextView) listItemView.findViewById(R.id.element_city);
        /// Display the location of the current earthquake in that TextView
        locationView.setText(city);


        /** This part of adapter takes care of the date and time*/
        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentQuake.getmDate());

        //  Find the TextView in the list_item.xml layout with the ID element_date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.element_date);
        //format the dateObject from Unix to date format
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
        String dateToDisplay = dateFormatter.format((dateObject));
        // Display the date of the current earthquake in that TextView
        dateTextView.setText(dateToDisplay);

        //  Find the TextView in the list_item.xml layout with the ID element_time
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.element_time);
        //format the dateObject from Unix to time format
        SimpleDateFormat timeFormatter = new SimpleDateFormat("EEE HH:mm");
        String timeToDisplay = timeFormatter.format((dateObject));
        // Display the time of the current earthquake in that TextView
        timeTextView.setText(timeToDisplay);

        /** This part of adapter takes care of the website intent*/



        return listItemView;
    }


    private int getMagnitudeColor(double magnitude) {
        int magColor;

        //rounds the magnitude down an integer
        int intMagnitude = (int) magnitude;

        switch (intMagnitude) {
            case 0:
            case 1:
                magColor = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                magColor = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3:
                magColor = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                magColor = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                magColor = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                magColor = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                magColor = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                magColor = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
                magColor = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            default:
                magColor = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                break;
        }
        return magColor;
    }
}
