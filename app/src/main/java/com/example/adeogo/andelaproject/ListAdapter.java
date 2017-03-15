package com.example.adeogo.andelaproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * An {@link ListAdapter} knows how to create a list item layout for each github user
 * in the data source (a list of {@link GitHubUser} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed.
 */

public class ListAdapter extends ArrayAdapter<GitHubUser> {

    /**
     * Constructs a new {@link ListAdapter}.
     *
     * @param context of the app
     * @param userArrayList is the list of github users, which is the data source of the adapter
     */    public ListAdapter(Context context, ArrayList<GitHubUser> userArrayList) {
        super(context, 0, userArrayList);
    }


    /**
     * Returns a list item view that displays information about the github user at the given position
     * in the list of github users.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if there is an existing list item view (called convertView) ,
        // if convertView is null, we inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            // Find the github user at the given position in the list of the github users
            GitHubUser gitHubUser = getItem(position);

            // Find the ImageView with view ID profile_imageview and
            // inflate with images from the photoUrl using the "Glide" libraries to show the images.
            ImageView userImageView = (ImageView) listItemView.findViewById(R.id.profile_imageview);
            Glide.with(userImageView.getContext())
                    .load(gitHubUser.getPhotoUrl())
                    .into(userImageView);

            // Find the TextView with view ID username_textview
            TextView usernameTextview = (TextView) listItemView.findViewById(R.id.username_textview);
            // Display the username of the current github user in that TextView
            usernameTextview.setText(gitHubUser.getUsername());
        }
        else {
            listItemView = (View) convertView.getTag();
        }
        return listItemView;
    }
}
