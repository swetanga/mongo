package com.nds.mongo.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;



import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;



@Entity
public class Posts {

	
	
	
	
	
	

	@Id
	
	
 private String postid;
	
	private String posttitle;
	
	
	private String postBlog;

	private String uname ;


	private Date createdDate;
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return "Posts [postid=" + postid + ", posttitle=" + posttitle + ", postBlog=" + postBlog + ", uname=" + uname
				+ ", createdDate=" + createdDate + "]";
	}



	public Posts(String postid, String posttitle, String postBlog, String uname, Date createdDate) {
		super();
		this.postid = postid;
		this.posttitle = posttitle;
		this.postBlog = postBlog;
		this.uname = uname;
		this.createdDate = createdDate;
	}



	public Posts() {super();}

	

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getPosttitle() {
		return posttitle;
	}

	public void setPosttitle(String posttitle) {
		this.posttitle = posttitle;
	}

	public String getPostBlog() {
		return postBlog;
	}

	public void setPostBlog(String postBlog) {
		this.postBlog = postBlog;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	



	


	

}