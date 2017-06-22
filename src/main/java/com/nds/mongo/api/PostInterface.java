package com.nds.mongo.api;

import java.util.List;



public interface PostInterface {

	
	public List<Posts> findPost(String postTitle) ;

	public double addpost(Posts post1);

	public void deletepost(double postid);
}
