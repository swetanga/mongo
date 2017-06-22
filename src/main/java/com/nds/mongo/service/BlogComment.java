package com.nds.mongo.service;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.nds.mongo.api.CommentInterface;
import com.nds.mongo.api.Comments;
import com.nds.mongo.api.Posts;
import com.nds.mongo.data.CommentDAO;
import com.nds.mongo.data.PostDAO;

public class BlogComment implements CommentInterface {

	@Override
	public void addComment(Comments comment) {
		MongoClient mongoClient = new MongoClient("10.197.24.69:27017");
		Morphia morphia = new Morphia();
		String databaseName = "cmad";
		Datastore datastore = morphia.createDatastore(mongoClient, databaseName);
		
		System.out.println("I am in Blogpost");
		
		CommentDAO commentdao = new CommentDAO(Comments.class, datastore);
			commentdao.savecomment(comment);

	}

	@Override
	public void deleteComment(double comment) {
		MongoClient mongoClient = new MongoClient("10.197.24.69:27017");
		Morphia morphia = new Morphia();
		String databaseName = "cmad";
		Datastore datastore = morphia.createDatastore(mongoClient, databaseName);
		
		
		
		CommentDAO commentdao = new CommentDAO(Comments.class, datastore);
			commentdao.removecomment(comment);

		
	}

	@Override
	public List<BasicDBObject> findComment(String comment) {
		MongoClient mongoClient = new MongoClient("10.197.24.69:27017");
		Morphia morphia = new Morphia();
		String databaseName = "cmad";
		Datastore datastore = morphia.createDatastore(mongoClient, databaseName);
		System.out.println("In cmt blog id= "+ comment);
		CommentDAO commentdao = new CommentDAO(Comments.class, datastore);
		List<BasicDBObject> cmtlist = commentdao.SearchComment(comment);
		
		for (BasicDBObject temp : cmtlist) {
					System.out.println(temp);
				}
		return cmtlist;
	}

}
