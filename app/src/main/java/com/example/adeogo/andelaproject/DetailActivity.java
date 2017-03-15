package com.example.adeogo.andelaproject;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    private String mProfilename;
    private String mProfileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(R.string.detail_activity_title);
        Intent intent = getIntent();
        mProfilename = intent.getStringExtra("profileName");
        String profileImageUrl = intent.getStringExtra("profileImageUrl");
        mProfileUrl = intent.getStringExtra("profileUrl");
        TextView profileTextView = (TextView) findViewById(R.id.profile_name);
        ImageView profileImageView = (ImageView) findViewById(R.id.profile_image);
        TextView profileUrlView = (TextView) findViewById(R.id.profile_url);

        profileTextView.setText(mProfilename);
        profileUrlView.setText(mProfileUrl);

        Glide.with(profileImageView.getContext())
                .load(profileImageUrl)
                .into(profileImageView);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void shareButton(View v){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @ " + mProfilename + ", "+ mProfileUrl);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, getResources().getText(R.string.send_to)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
