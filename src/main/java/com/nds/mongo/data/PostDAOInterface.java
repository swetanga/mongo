package com.nds.mongo.data;

import java.util.List;

import com.nds.mongo.api.Posts;



public interface PostDAOInterface {
	
	public List<Posts> SearchPost(String postTitle);

}
