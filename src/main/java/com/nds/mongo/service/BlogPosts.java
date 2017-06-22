package com.nds.mongo.service;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.nds.mongo.api.PostInterface;
import com.nds.mongo.api.Posts;
import com.nds.mongo.data.PostDAO;
import com.nds.mongo.data.PostDAOInterface;

public class BlogPosts implements PostInterface{

	//private PostDAO postdao1 =  new PostDAO(null, null);
	
	public List<Posts> findPost(String postTitle) {
		System.out.println("title in blog "+ postTitle);
		MongoClient mongoClient = new MongoClient("10.197.24.69:27017");
		Morphia morphia = new Morphia();
		String databaseName = "cmad";
		Datastore datastore = morphia.createDatastore(mongoClient, databaseName);

		PostDAO postdao = new PostDAO(Posts.class, datastore);

		
		List<Posts> postlist = postdao.SearchPost(postTitle);
		
		return postlist;
	}

	
	public double addpost(Posts post1) {
		
		double newpostid=0.0;
		MongoClient mongoClient = new MongoClient("10.197.24.69:27017");
		Morphia morphia = new Morphia();
		String databaseName = "cmad";
		Datastore datastore = morphia.createDatastore(mongoClient, databaseName);
		
		System.out.println("I am in Blogpost");
		
		PostDAO postdao = new PostDAO(Posts.class, datastore);
			newpostid =postdao.savepost(post1);
		
			return newpostid;
	}


	@Override
	public void deletepost(double postid) {
		
		MongoClient mongoClient = new MongoClient("10.197.24.69:27017");
		Morphia morphia = new Morphia();
		String databaseName = "cmad";
		Datastore datastore = morphia.createDatastore(mongoClient, databaseName);

		PostDAO postdao = new PostDAO(Posts.class, datastore);
		
		postdao.removepost(postid);
	}

}
