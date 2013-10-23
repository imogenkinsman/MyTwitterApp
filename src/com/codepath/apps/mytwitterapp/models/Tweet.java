package com.codepath.apps.mytwitterapp.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tweet")
public class Tweet extends Model implements Serializable {
	
	private static final long serialVersionUID = 108450517750950620L;
	
// Define database columns and associated fields
	@Column(name = "user")
	private User user;
	@Column(name = "tweetId")
	private long tweetId;
	@Column(name = "timestamp")
	private String timestamp;
	@Column(name = "body")
	private String body;

  // Make sure to always define this constructor with no arguments
	public Tweet() {
		super();
	}
  
//Add a constructor that creates an object from the JSON response
 public static Tweet fromJson(JSONObject object){
	 Tweet tweet = new Tweet();

   try {
     tweet.tweetId = object.getLong("id");
     tweet.timestamp = object.getString("created_at");
     tweet.body = object.getString("text");
     tweet.user = User.fromJson(object.getJSONObject("user"));
     
     Log.d("DEBUG", Long.toString(tweet.tweetId));
     
     // add user to db if doesn't exist
//     if (User.getFromId(this.userId) == null) {
//    	 User u = new User(object.getJSONObject("user"));
//    	 u.save();
//     }
     
   } catch (JSONException e) {
     e.printStackTrace();
     return null;
   }
   return tweet;
 }

 public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
   ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

   for (int i=0; i < jsonArray.length(); i++) {
       JSONObject tweetJson = null;
       try {
           tweetJson = jsonArray.getJSONObject(i);
       } catch (Exception e) {
           e.printStackTrace();
           continue;
       }

       Tweet tweet = Tweet.fromJson(tweetJson);
       tweet.save();
       tweets.add(tweet);
   }

   return tweets;
 }
 
 public User getUser() {
	 return user;
 }
 
 public String getBody() {
	 return body;
 }
 
 public String getTimeStamp() {
	 return timestamp;
 }
 
 public long getTweetId() {
	 return tweetId;
 }
 
}