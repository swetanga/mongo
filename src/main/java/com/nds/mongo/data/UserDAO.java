package com.nds.mongo.data;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.query.Query;

import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.nds.mongo.api.Posts;
import com.nds.mongo.api.User;

public class UserDAO implements UserDAOInterface {

	
	
		private static final String dbName = "cmad";
		private static DB db;
		
		
		public void createUser(User user){
			
			 try{
					String uname = user.getUsername();
					String passwd= user.getPassword();
					System.out.println("uname is in DAO " + uname);
					System.out.println("passwd is in DAO " + passwd);
		         // To connect to mongodb server
		        // MongoClient mongoClient = new MongoClient( "35.188.87.30" , 27017 );
					MongoClient mongoClient = new MongoClient( "10.197.24.69" , 27017 );
		         // Now connect to your databases
		         
				 db = mongoClient.getDB(dbName);
		         System.out.println("Connect to database successfully");
		         DBCollection coll = db.getCollection("User"); 
		         BasicDBObject doc = new BasicDBObject();
		         doc.put("username", uname);
		         doc.put("password", passwd);
		        
		         coll.insert(doc);
		       //  boolean auth = db.authenticate(myUserName, myPassword);
		        // System.out.println("Authentication: "+auth);
					
		      }catch(Exception e){
		         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      }
			
			
			
		} //create user


		@Override
		public void removeuser(String uname) {
			
			 MongoClient mongoClient = new MongoClient( "10.197.24.69" , 27017 );
			db = mongoClient.getDB(dbName);
	         System.out.println("Connect to database successfully for removing user " + uname);
	         DBCollection coluser = db.getCollection("User");
	         DBCollection colpost = db.getCollection("Posts");
	         DBCollection colcomment = db.getCollection("Comments");
	       
	         BasicDBObject whereQuery = new BasicDBObject();
	         whereQuery.put("username", uname);
	         DBCursor cursor = colcomment.find(whereQuery);
	         while(cursor.hasNext()) {
	             colcomment.remove(cursor.next());
	         }   
	    //     colcomment.remove(new BasicDBObject().append("username", uname));
	        
	         
	         BasicDBObject whereQuery1 = new BasicDBObject();
	         whereQuery1.put("username", uname);
	         DBCursor cursor1 = colpost.find(whereQuery1);
	         while(cursor1.hasNext()) {
	             colpost.remove(cursor1.next());
	         }   
		
	           coluser.remove(new BasicDBObject().append("username", uname));
	         
		
		}


		@Override
		public List<BasicDBObject> checkuser(String login, String passwd) {
			
			//List<BasicDBObject> lst1 = new ArrayList<Posts>();
			List<BasicDBObject> lst1 = new ArrayList<BasicDBObject>();
		
			MongoClient mongoClient = new MongoClient( "10.197.24.69" , 27017 );
			db = mongoClient.getDB(dbName);
	         System.out.println("Connect to database successfully for checking1 user " + login);
	         DBCollection coluser = db.getCollection("User");
	         DBCollection colpost = db.getCollection("Posts");
	         DBCollection colcomment = db.getCollection("Comments");
		
	         BasicDBObject whereQuery1 = new BasicDBObject();
	         whereQuery1.put("username", login);
	         DBObject doc = coluser.findOne(whereQuery1);
	         String pass = (String) doc.get("password");
	         System.out.println("password is in db: "+ pass);
	         
	         
	         
	         BasicDBObject whereQuery2 = new BasicDBObject();
	         whereQuery2.put("uname", login);
	         Cursor cur = colpost.find(whereQuery2);
	         while(cur.hasNext()) {
	        //   System.out.println(cur.next());
	         lst1.add( (BasicDBObject) cur.next());
	         }  
	         
	 		return lst1;
	    // if(pass.equals(passwd)){System.out.println("password match"); return lst1;}
	//	else{System.out.println("password didn't match"); lst1=null;return lst1;}
		}


		@Override
		public List<BasicDBObject> searchuserpost(String user1) {
			List<BasicDBObject> lst1 = new ArrayList<BasicDBObject>();
			
			MongoClient mongoClient = new MongoClient( "10.197.24.69" , 27017 );
			db = mongoClient.getDB(dbName);
	         System.out.println("Connect to database successfully for finding post of user " + user1);
	         DBCollection coluser = db.getCollection("User");
	         DBCollection colpost = db.getCollection("Posts");
			
	         BasicDBObject whereQuery2 = new BasicDBObject();
	         whereQuery2.put("uname", user1);
	         Cursor cur = colpost.find(whereQuery2);
	         while(cur.hasNext()) {
	        //   System.out.println(cur.next());
	         lst1.add( (BasicDBObject) cur.next());
	         }  
	         
	         return lst1;
		}
		
	}

	
	

