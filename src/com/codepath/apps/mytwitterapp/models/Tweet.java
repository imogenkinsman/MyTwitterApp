package com.codepath.apps.mytwitterapp.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tweets")
public class Tweet extends Model {
  // Define database columns and associated fields
  @Column(name = "userId")
  private String userId;
  @Column(name = "userHandle")
  private String userHandle;
  @Column(name = "timestamp")
  private String timestamp;
  @Column(name = "body")
  private String body;
  
  @Column(name = "User")
  public User user;

  // Make sure to always define this constructor with no arguments
  public Tweet() {
    super();
  }
  
//Add a constructor that creates an object from the JSON response
 public Tweet(JSONObject object){
   super();

   try {
     this.userId = object.getString("user_id");
     this.userHandle = object.getString("user_username");
     this.timestamp = object.getString("timestamp");
     this.body = object.getString("body");
   } catch (JSONException e) {
     e.printStackTrace();
   }
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

       Tweet tweet = new Tweet(tweetJson);
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
 
}