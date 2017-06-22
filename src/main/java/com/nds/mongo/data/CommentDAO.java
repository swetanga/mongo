package com.nds.mongo.data;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.nds.mongo.api.Comments;
import com.nds.mongo.api.Posts;



public class CommentDAO extends BasicDAO<Comments,String>{

	public CommentDAO(Class<Comments> entityClass, Datastore ds) {
		super(entityClass, ds);
		// TODO Auto-generated constructor stub
	}


	private static final String dbName = "cmad";
	private static DB db;


public void savecomment(Comments comment){
	
	try{
		String dbuname="";
	System.out.println("inside savecomment DAO");
	String commentdata = comment.getCommentdata();
	double postid = comment.getPostid();
	String uname = comment.getUname();
	Date date1 = new Date();
	String newdate = date1.toString();
	
	MongoClient mongoClient = new MongoClient( "10.197.24.69" , 27017 );
	
    // Now connect to your databases
    
	 db = mongoClient.getDB(dbName);
	
	DBCollection coll = db.getCollection("Comments"); 
	DBCollection col2 = db.getCollection("sequence");
	DBCollection col3 = db.getCollection("User");
  
	
	
	BasicDBObject whereQuery2 = new BasicDBObject();
	whereQuery2.put("username", uname);
	
	
	DBObject doc1 =col3.findOne(whereQuery2);
	if(doc1 !=null){
	 dbuname=		(String) doc1.get("username");
	System.out.println("dbusername is " + dbuname);
	System.out.println("received user name is " + uname);
	}
	if(dbuname.equals(uname))
	{
	
	BasicDBObject doc = new BasicDBObject();
  
    BasicDBObject newDocument =
    		new BasicDBObject().append("$inc",
    		new BasicDBObject().append("commentid", 1));

    	col2.update(new BasicDBObject().append("allids", "all"), newDocument);
    
    
    
    
    
     DBObject doc2 = col2.findOne();
    //System.out.println("this is sequence "+ doc2.get("postid"));
   double idpost = (double) doc2.get("commentid");
   
   System.out.println("commenttid is " + idpost);
   
   
   doc.put("commentid", idpost);
   doc.put("commentdata", commentdata);
    doc.put("postid", postid);
    doc.put("username", uname);
    doc.put("createdDate", newdate);
    
    coll.insert(doc);
	}
	else{ System.out.println("User doesn't match");}
	}catch (MongoException e) {
		e.printStackTrace();
	}
    
}


public void removecomment(double commentid1) {
	try{
	MongoClient mongoClient = new MongoClient( "10.197.24.69" , 27017 );
	db = mongoClient.getDB(dbName);
	DBCollection col3 = db.getCollection("Comments");
	
	System.out.println("commentid to delete is (in DAO)" + commentid1);
	BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("commentid", commentid1);
	  col3.findOne(whereQuery);
	DBObject doc3 = col3.findOne(whereQuery);
	col3.remove(doc3);
	} catch (MongoException e) {
		e.printStackTrace();
	}
}


public List<BasicDBObject> SearchComment(String comment) {
	try{
		System.out.println("In cmt dao id= "+ comment);
		Integer value = Integer.parseInt(comment);
		//Query<Comments> query = createQuery().field("postid").equal(comment);
		//return query.asList();
		List<BasicDBObject> lstcmt1 = new ArrayList<BasicDBObject>();
		
		MongoClient mongoClient = new MongoClient( "10.197.24.69" , 27017 );
		db = mongoClient.getDB(dbName);
		DBCollection colcmt = db.getCollection("Comments");
		
		
		BasicDBObject whereQuery1 = new BasicDBObject();
		whereQuery1.put("postid", value);
		
		DBCursor cr2  = colcmt.find(whereQuery1);
		
		while(cr2.hasNext()) {
             lstcmt1.add((BasicDBObject) cr2.next());
			//System.out.println(cr2.next());
         } 
		
		///for (BasicDBObject temp : lstcmt1) {
	///		System.out.println(temp);
	///	}	
		
		return lstcmt1;
		
		
	}catch(Exception e){e.printStackTrace();return null;}
	
}




}
