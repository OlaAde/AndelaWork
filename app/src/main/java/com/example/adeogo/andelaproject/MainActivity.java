package com.example.adeogo.andelaproject;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<GitHubUser>> {

    /** Adapter for the list of github users */
    private ListAdapter mListAdapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;

    /** URL for github user data from the GitHub */
    private static final String mUrl = "https://api.github.com/search/users?q=location:lagos+language:java&sort=stars&order=desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a new adapter that takes an empty list of github users as input
        mListAdapter = new ListAdapter(this, new ArrayList<GitHubUser>());

        // Find a reference to the {@link ListView} in the layout
        ListView listView = (ListView) findViewById(R.id.listView);

        // Set an item click listener on the ListView, which sends an intent
        // to open the Detail Activity with more information about the selected github user.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Find the current github user that was clicked on
                GitHubUser currentUser = mListAdapter.getItem(i);

                String profileName = currentUser.getUsername();
                // Get and store the Image Url and the profile Url of the github user
                String profilleImageUrl = currentUser.getPhotoUrl();
                String profileUrl = currentUser.getUrl();

                // Create a new intent to open the Detail Activity
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                // Add extras to the intent
                intent.putExtra("profileName", profileName);
                intent.putExtra("profileImageUrl", profilleImageUrl);
                intent.putExtra("profileUrl", profileUrl);
                startActivity(intent);
            }
        });

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        listView.setAdapter(mListAdapter);
        getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<GitHubUser>> onCreateLoader(int i, Bundle bundle) {

        return new GitHubLoader(this, mUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<GitHubUser>> loader, List<GitHubUser> data) {
        // Create a new adapter that takes the list of users as input
        mListAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mListAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<GitHubUser>> loader) {
        mListAdapter.addAll(new ArrayList<GitHubUser>());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.action_settings) {

        }
        return super.onOptionsItemSelected(menuItem);
    }

}
