package com.nds.mongo.service;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.nds.mongo.api.Posts;
import com.nds.mongo.api.User;
import com.nds.mongo.api.UserInterface;
import com.nds.mongo.data.*;

public class BlogUsers implements UserInterface {

	private UserDAOInterface userdao = new UserDAO();
	@Override
	public void addUser(User user) {
		if(user.getUsername()==null)
			System.out.println("username received is null");
		
		
		System.out.println("in blogUsers");
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		
		userdao.createUser(user);
		
	}
	@Override
	public void deleteuser(String uname) {
		userdao.removeuser(uname);
		
	}
	@Override
	public List<BasicDBObject> checkuser(String login, String passwd) {
		
		List<BasicDBObject> yourList = new ArrayList<BasicDBObject>();
		yourList= userdao.checkuser(login,passwd);
		 
		//for (BasicDBObject temp : yourList) {
		//	System.out.println(temp);
		//}
		return yourList;
		
	}
	@Override
	public List<BasicDBObject> finduserPost(String user1) {
	return	userdao.searchuserpost(user1);
		//return null;
	}
	
	
	

}
