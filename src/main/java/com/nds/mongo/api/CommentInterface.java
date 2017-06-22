package com.nds.mongo.api;

import java.util.List;
import com.mongodb.BasicDBObject;
public interface CommentInterface {

	public void addComment(Comments comment);

	public void deleteComment(double comment);

	public List<BasicDBObject> findComment(String pstid);
	
	
}
