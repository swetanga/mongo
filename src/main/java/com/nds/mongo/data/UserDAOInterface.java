package com.nds.mongo.data;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.nds.mongo.api.Posts;
import com.nds.mongo.api.User;

public interface UserDAOInterface {

	public void createUser(User user) ;

	public void removeuser(String uname);

	public List<BasicDBObject> checkuser(String login, String passwd);

	public List<BasicDBObject> searchuserpost(String user1);
}
