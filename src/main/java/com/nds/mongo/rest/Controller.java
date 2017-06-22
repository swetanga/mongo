package com.nds.mongo.rest;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.BasicDBObject;
import com.nds.mongo.api.CommentInterface;
import com.nds.mongo.api.Comments;
import com.nds.mongo.api.PostInterface;
import com.nds.mongo.api.Posts;
import com.nds.mongo.api.User;
import com.nds.mongo.api.UserInterface;
import com.nds.mongo.service.BlogComment;
import com.nds.mongo.service.BlogPosts;
import com.nds.mongo.service.BlogUsers;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/Blog")

public class Controller {

	private static UserInterface user2 = new BlogUsers();
	private static PostInterface post2 = new BlogPosts();
	private static CommentInterface comment2 = new BlogComment();
	
	 
	    private KeyGenerator keyGenerator;
	 
	
	@Path("/User")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	
	public Response add(User h1) {
			//System.out.println("H password is " + h1.getPassword());
			//System.out.println("H username is " + h1.getUsername());
			user2.addUser(h1);
			return Response.ok()
					.build();
		
	}
	
	
	/////-----------get post of a user-----------//////////////
	
	
	@GET
	@Path("User/{username}/Posts")

	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response finduserPost(@PathParam("username") String user1)
	//public Response find(User user4)
	{
		List<BasicDBObject> p1=null;
	System.out.println(user1);
	p1 =(List<BasicDBObject>) user2.finduserPost(user1);
	//GenericEntity <List<BasicDBObject>>enty2 = new GenericEntity<List<BasicDBObject>>(p1){};


		return Response.ok(p1.toString()).build();
	} // user method
	
/////-----------get post of a user-----------//////////////
	
	
	
	
	
	
	
	@Path("/User/login")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	//@JWTTokenNeeded
	public Response login(@FormParam("username") String login, @FormParam("password") String passwd)
	{
		try{
			
			List<BasicDBObject> lst1 = new ArrayList<BasicDBObject>();
			List<Posts> lst2 = new ArrayList<Posts>();
			
			System.out.println("login is "+ login);
			System.out.println("password " +passwd);
		   lst1=   user2.checkuser(login,passwd);
			String token = issueToken(login);
			 
		   for (BasicDBObject temp : lst1) {
			   System.out.println(temp);
			//lst2.add(temp);
		   }
		  //lst1.add(new BasicDBObject().append("jwttoken", token));
		   
			System.out.println("new token is:-   " + token);
			// Return the token on the response
		//	GenericEntity<List<BasicDBObject>> enty1 = new GenericEntity<List<BasicDBObject>> (lst1){};
				 
			//Response.ok()
	           return Response.ok(lst1.toString()).header(HttpHeaders.AUTHORIZATION, "Bearer"+token).header("Access-Control-Allow-Origin", "*").header("Access-Control-Expose-Headers", "POST,GET,PUT,DELETE").header("Access-Control-Allow-Credentials", "true").header("Access-Control-Allow-Headers", "Authorization").build();
	         //  return Response.ok(lst1.toString()).build();
	           
	
	        } catch (Exception e) {
	        	System.out.println("wrong token");
	        	return Response.status(401).build();
	        }
	
		}	
	
	@Path("/User/{username}")
	@DELETE
	///@JWTTokenNeeded
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
		
	public Response DeleteUser(@PathParam("username") String uname)
	{
		String s1 = "Can't delete user!";
		String s2 = "User Deleted!";
		
		if(uname !=null)
		user2.deleteuser(uname);
		else
		return Response.status(401).entity(s1).build();	
		
		
		return Response.ok().entity(s2).build();
		
	}
	
	//------------------------------------------posts-------------------------------------
	
	@Path("/Posts/{title}")
	@GET
	
	@Produces(MediaType.APPLICATION_JSON)
	public Response findtitlepost(@PathParam("title") String title){ 
		
		System.out.println("title is "+ title);
		 List <Posts> postlist1 = post2.findPost(title);
		
		System.out.println("final postlist is "+ postlist1);
		
		GenericEntity<List<Posts>> enty1 = new GenericEntity<List<Posts>> (postlist1){};
		
		return Response.ok(enty1).build();
	}  // find titlepost
	
	
	
	@Path("/Posts")
	@POST
	//@JWTTokenNeeded
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response addnewpost(Posts post1)
	{
		double newpostid=0.0;
		newpostid=post2.addpost(post1);
		String s1 = String.valueOf(newpostid);
		return Response.ok(s1).build();
	} // addnew post
	
	
	@Path("/Posts/{postid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	
	public Response deletepost(@PathParam("postid")  String postid)
	{
		double value = Double.parseDouble(postid);
		post2.deletepost(value);
		return Response.ok().build();
	}
	
	/////////---------------comments section ----------------------/////////////
	
	
	
	@Path("/Comments/{comment}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response findcomment(@PathParam("comment") String pstid )
	{
		List<BasicDBObject> lstcmt = new ArrayList<BasicDBObject>();
		System.out.println("In cmt controller id= "+ pstid);
		lstcmt= comment2.findComment(pstid);
		return Response.ok(lstcmt.toString()).build();
	}
	
	
	@Path("/Comments")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response addcomment(Comments comment )
	{
		//double cmtid=0.0;
		comment2.addComment(comment);
		
		//String s1 = String.valueOf(newpostid);
		return Response.ok().build();
	}
	
	
	@Path("/Comments/{comment}")
	@DELETE
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response deletecomment(@PathParam("comment") String comment )
	{
		double value1 = Double.parseDouble(comment); 
		comment2.deleteComment(value1);
		return Response.ok().build();
	}
	
	
	
	

private   String issueToken(String login) {
		 
		 
		 Calendar calendar = Calendar.getInstance();
		 calendar.add(Calendar.MINUTE, 30);
	Date date1 = calendar.getTime();	 
		 
		// Key key = keyGenerator.generateKey();  
	//Key key = "secret".getBytes("UTF-8");	 
	
	try{
	        String jwtToken = Jwts.builder()
	                .setSubject(login)
	                //.setIssuer(uriInfo.getAbsolutePath().toString())
	                .setIssuedAt(new Date())
	                .setExpiration(date1)
	                .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
	                .compact();
	        return jwtToken;
	    }catch(Exception e){
	    	System.out.println(" problem in creating token");
	    	return null;
	    }
}
	
} // class
