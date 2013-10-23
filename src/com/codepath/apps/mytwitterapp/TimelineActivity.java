package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	private static final int REQUEST_CODE = 0;
	TweetsAdapter twtAdapter;
	ListView lvTweets;
	
	long minId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupViews();
		
		minId = Long.MAX_VALUE;
		
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				setMinId(tweets);
					
				twtAdapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(twtAdapter);
			}
		});
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				MyTwitterApp.getRestClient().getHomeTimeLine(minId, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
						setMinId(tweets);
						
						twtAdapter.addAll(tweets);
					}
				});
			}
		});
	}
	
	private void setMinId(ArrayList<Tweet> tweets){
		for (Tweet t : tweets) {
			if (t.getId() < minId) {
				Log.d("DEBUG", t.getId().toString());
				minId = t.getId();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void setupViews() {
		lvTweets = (ListView) findViewById(R.id.lvTweets);
	}
	
	public void onComposeAction(MenuItem mi) {
		Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == 1) {
			Tweet newTweet = (Tweet) data.getSerializableExtra("tweet");
			twtAdapter.insert(newTweet, 0);
			Toast.makeText(this, "Tweet Posted", Toast.LENGTH_SHORT).show();
		}
	}

}
