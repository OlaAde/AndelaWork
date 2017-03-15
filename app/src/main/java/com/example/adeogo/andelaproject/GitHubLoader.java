package com.example.adeogo.andelaproject;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of github users by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class GitHubLoader extends AsyncTaskLoader {

    /** Query URL */
    private String mUrl = null;

    /**
     * Constructs a new {@link GitHubLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public GitHubLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<GitHubUser> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract the list of github users.
        List<GitHubUser> arrayList = QueryUtils.extractUsers(mUrl);
        return arrayList;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
