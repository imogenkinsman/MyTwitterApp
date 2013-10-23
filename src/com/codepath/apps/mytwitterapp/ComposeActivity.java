package com.codepath.apps.mytwitterapp;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {
	
	private static final int TWEET_SUCCESS = 1;
	
	EditText etTweet;
	Button btnTweet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		setupViews();
		addListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public void setupViews() {
		etTweet = (EditText) findViewById(R.id.etTweet);
		btnTweet = (Button) findViewById(R.id.btnTweet);
		btnTweet.setEnabled(false);
	}
	
	public void addListeners() {
		//disable the tweet button when there's no body content
		etTweet.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() == 0){
					btnTweet.setEnabled(false);
				} else {
					btnTweet.setEnabled(true);
				}
			}
		});
	}
	
	public void tweet(View v) {
		String messageBody = etTweet.getText().toString();
		
		MyTwitterApp.getRestClient().postTweet(messageBody, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonTweet) {
				Tweet tweet = Tweet.fromJson(jsonTweet);
				
				Intent data = new Intent();
				data.putExtra("tweet", tweet);
				setResult(TWEET_SUCCESS, data);
				finish();
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject error) {
				Log.e("ERROR", e.toString());
				Toast.makeText(ComposeActivity.this, "Unable To Post Tweet", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void cancel(View v) {
		finish();
	}

}