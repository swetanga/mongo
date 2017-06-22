package com.nds.mongo.api;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Comments {

	@Id
	private Double commentId;
	private String commentdata;
	private Date createdDate;
	private String uname;
	private Double postid;
	
	
	
	public Comments() {super();}
		
	
	public Comments(Double commentId, String commentdata, Date createdDate, String uname, Double postid) {
		super();
		this.commentId = commentId;
		this.commentdata = commentdata;
		this.createdDate = createdDate;
		this.uname = uname;
		this.postid = postid;
	}
	public Double getCommentId() {
		return commentId;
	}
	public void setCommentId(Double commentId) {
		this.commentId = commentId;
	}
	public String getCommentdata() {
		return commentdata;
	}
	public void setCommentdata(String commentdata) {
		this.commentdata = commentdata;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Double getPostid() {
		return postid;
	}
	public void setPostid(Double postid) {
		this.postid = postid;
	}
}
