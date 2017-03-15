package com.example.adeogo.andelaproject;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving github user data from GitHub.
 */
public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {

    }

    public static List<GitHubUser> extractUsers(String url) {
        // Create URL object
        URL Url = createUrl(url);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(Url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Extract relevant fields from the JSON response and create a list of GitHubUsers
        List<GitHubUser> gitHubUserList = formatJson(jsonResponse);
        // Return the list of Github users
        return gitHubUserList;
    }

    public static List<GitHubUser> formatJson(String jsonData) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding guthub users to.
        List<GitHubUser> gitHubUsers = new ArrayList<>();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject jsonObject = new JSONObject(jsonData);
            // To Extract the JSONArray associated with the key called "items",
            // which represents a list of features (or github users).
            JSONArray usersArray = jsonObject.getJSONArray("items");

            // For each github user in the github user Array, create I created a GithubUser object
            for (int i = 0; i < usersArray.length(); i++) {

                // Get a single github user at position i within the list of github users
                JSONObject position = usersArray.getJSONObject(i);

                // Extract the value for the key called "login"
                String username = position.getString("login");
                // Extract the value for the key called "avatar_url"
                String photoUrl = position.getString("avatar_url");
                // Extract the value for the key called "html_url"
                String profileUrl = position.getString("html_url");

                // Create a new GitHubUser object with the username, photoUrl, profileUrl.
                gitHubUsers.add(new GitHubUser(username,photoUrl,profileUrl));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the githubuser JSON results", e);
        }

        // Return the list of gitHubUsers
        return gitHubUsers;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    public static URL createUrl(String StringUrl) {
        URL url = null;
        try {
            url = new URL(StringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code :" + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


}
