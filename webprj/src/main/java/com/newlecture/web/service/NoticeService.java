package com.newlecture.web.service;

import java.sql.SQLException;
import java.util.List;

import com.newlecture.web.entity.Notice;

public interface NoticeService {

	List<Notice> getList(int page,String field,String query) throws SQLException, ClassNotFoundException;

	//Scalar
	int getCount() throws ClassNotFoundException, SQLException;
	
	int insert(Notice notice) throws ClassNotFoundException, SQLException;

	int update(Notice notice) throws ClassNotFoundException, SQLException; 
	
	int delet(int id) throws ClassNotFoundException, SQLException; 

}
