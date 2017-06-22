package com.nds.mongo.api;

import java.util.List;

import com.mongodb.BasicDBObject;

public interface UserInterface {

	public void addUser(User user);

	public void deleteuser(String uname);

	public List<BasicDBObject> checkuser(String login, String passwd);

	public List<BasicDBObject> finduserPost(String user1);
	
}
