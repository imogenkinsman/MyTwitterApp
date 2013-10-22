package com.codepath.apps.mytwitterapp.models;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Users")
public class User extends Model {
	// Define database columns and associated fields
	@Column(name = "userId")
	private String userId;
	@Column(name = "name")
	private String name;
	@Column(name = "profileImgUrl")
	private String profileImgUrl;
	@Column(name = "screenName")
	private String screenName;
	
	public User() {
		super();
	}
	
	// https://dev.twitter.com/docs/api/1.1/get/users/show
	public User(JSONObject object) {
		super();
		
		try {
			this.userId = object.getString("id");
			this.profileImgUrl = object.getString("profile_image_url");
			this.name = object.getString("name");
			this.screenName = object.getString("screen_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
//	public static User fromJson(JSONObject json) {
//		
//	}
	
	public List<Tweet> tweets() {
		return getMany(Tweet.class, "User");
	}
	
	public String getProfileImageUrl() {
		return profileImgUrl;
	}
	
	public String getName() {
		return name;
	}
	
	public String getScreenName() {
		return screenName;
	}

	public static User getFromId(String userId) {
		return new Select().from(User.class).where("userId = ?", userId).executeSingle();
	}
	
}
