package com.nds.mongo.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.query.Query;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.nds.mongo.api.Posts;

import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;


public class PostDAO extends BasicDAO<Posts,String> {

	public PostDAO(Class<Posts> entityClass, Datastore ds) {
		super(entityClass, ds);
	}


	private static final String dbName = "cmad";
	private static DB db;
	
	
	
	public List<Posts> SearchPost(String posttitle1) {
		//String total2 = Double.toString(posttitle);
		  System.out.println("Posttitle search (POSTDAO): " + posttitle1);
		try{
		Query<Posts> query = createQuery().field("posttitle").contains(posttitle1);
		
		/*
		DBCollection coll = db.getCollection("Posts"); 
			List<Posts> postlist1 = new ArrayList<Posts>();
			
			BasicDBObject whereQuery2 = new BasicDBObject();
			whereQuery2.put("posttitle", posttitle1);
			
			DBCursor cursor = coll.find(whereQuery2);
		
			while(cursor.hasNext()){
			postlist1.add( (Posts) cursor.next());
			}
			
			*/
		return query.asList();
		//return postlist1;
		}catch (MongoException e) {
			System.out.println("something wrong in finding posts");
			e.printStackTrace();
			return null;
		}
		
		
		
	}


	public  double savepost(Posts post1) {
		
		int httpstatus = 200;
		double newpostid=0.0;
		
		try{
		System.out.println("inside savepost DAO");
		String posttitle = post1.getPosttitle();
		String postBlog = post1.getPostBlog();
		String uname = post1.getUname();
		
		System.out.println("received username is (savepost)" + uname ); 
		Date date1 = new Date();
		String newdate = date1.toString();
		MongoClient mongoClient = new MongoClient( "10.197.24.69" , 27017 );
		
        // Now connect to your databases
        
		 db = mongoClient.getDB(dbName);
		
		DBCollection coll = db.getCollection("Posts"); 
		DBCollection col2 = db.getCollection("sequence");
		DBCollection col3 = db.getCollection("User");
		
		BasicDBObject whereQuery2 = new BasicDBObject();
		whereQuery2.put("username", uname);
		
		
		DBObject doc1 =col3.findOne(whereQuery2);
String dbuname=		(String) doc1.get("username");
	System.out.println("db uname is "+dbuname);	
	System.out.println("received username is "+  uname);
	
	if(dbuname.equals(uname)){
        BasicDBObject doc = new BasicDBObject();
      
        BasicDBObject newDocument =
        		new BasicDBObject().append("$inc",
        		new BasicDBObject().append("postid", 1));

        	col2.update(new BasicDBObject().append("allids", "all"), newDocument);
        
        
        
        
        
         DBObject doc2 = col2.findOne();
        //System.out.println("this is sequence "+ doc2.get("postid"));
       double idpost = (double) doc2.get("postid");
       
       System.out.println("poistid is " + idpost);
       
       
       doc.put("postid", idpost);
       doc.put("posttitle", posttitle);
        doc.put("postBlog", postBlog);
        doc.put("uname", uname);
        doc.put("createdDate", newdate);
        
        coll.insert(doc);
        newpostid = idpost;
        
		} // user is found in user table
		
		
		}catch (MongoException e) {
			e.printStackTrace();
		  
		}
		return newpostid;
	}


	public void removepost(double postid1) {
			
			try{
			MongoClient mongoClient = new MongoClient( "10.197.24.69" , 27017 );
			db = mongoClient.getDB(dbName);
			DBCollection col3 = db.getCollection("Posts");
			DBCollection colcmt = db.getCollection("Comments");
			
			System.out.println("postid to delete is (in DAO)" + postid1);
			
 //////////////------- delete comments -------------------------
			BasicDBObject whereQuery1 = new BasicDBObject();
			whereQuery1.put("postid", postid1);
			 // col3.findOne(whereQuery1);
			DBCursor cr2  = colcmt.find(whereQuery1);
			while(cr2.hasNext()) {
	             colcmt.remove(cr2.next());
	         }  
			
//////////////------- delete comments -------------------------
			
			
	/// ----------delete posts -----------		
			BasicDBObject whereQuery = new BasicDBObject();
			String strpostid = String.valueOf(postid1);
			whereQuery.put("postid", postid1);
			  
			DBCursor cr1  = col3.find(whereQuery);
			col3.remove(whereQuery);
			while(cr1.hasNext()) {
	             col3.remove(cr1.next());
	         }  
			/// ----------delete posts -----------		
			
			
			
			
			} catch (MongoException e) {
				e.printStackTrace();
			}
	}
	
	
	
	/*
	public List<Posts> SearchPost(String postTitle) {
		 List<Posts> post1 = new ArrayList<>();

		 try{
			 MongoClient mongoClient = new MongoClient( "10.197.24.69" , 27017 );
				
	         // Now connect to your databases
	         
			 db = mongoClient.getDB(dbName);
	         System.out.println("Connect to database successfully");
	         DBCollection coll = db.getCollection("Posts");
			
	       
	         Query<Posts> query = createQuery().field("title").contains(title).field("isbn").lessThan(1000);
	 		return query.asList();
	         
	         
	        DBObject query = new BasicDBObject("posttitle", postTitle);
	        // DBObject dbObject = coll.findOne(query);
	        DBCursor dbcursor = coll.find(query);
	     
			 
			while (dbcursor.hasNext()){
			//	System.out.println(dbcursor.next());
		
				//post1 = (List<Posts>) dbcursor.next();
			String uname3 = (String) post3.get("uname");
			String blog3 = (String) post3.get("postBlog");
			post1.setPostBlog(uname3);
			post1.set
			///System.out.println("------------------------------");
			//System.out.println(blog3+"and "+ uname3);
			}
		 }catch(Exception e){
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
		 
		return post1;
	}
*/
}
