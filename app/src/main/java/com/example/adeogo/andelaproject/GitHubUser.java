package com.example.adeogo.andelaproject;

/**
 * A GithubUser contains information about a github user.
 */

public class GitHubUser {

    /** Username of the github user*/
    private String mUsername;
    /** Url to the profile photo of the github user */
    private String mPhotoUrl;
    /**Url to the profile of the github user*/
    private String mUrl;

    public GitHubUser(String Username, String PhotoUrl, String Url) {
        mUsername = Username;
        mPhotoUrl = PhotoUrl;
        mUrl = Url;
    }

    /**
     * Returns the Username of the github user
     */
    public String getUsername() {
        return mUsername;
    }

    /**
     * Returns the Url to the proile picture of the github user
     */
    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    /**
     * Returns the Url to the profile of the github user
     */
    public String getUrl() {
        return mUrl;
    }
}
