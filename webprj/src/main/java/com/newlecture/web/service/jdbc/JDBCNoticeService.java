package com.newlecture.web.service.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@Service	//@Controller, @Service, @Repository
public class JDBCNoticeService implements NoticeService{
	/*
		private String url="jdbc:mysql://localhost:3306/connectdb?characterEncoding=UTF-8&serverTimezone=UTC";
		private String uid="connectuser";
		private String pwd="connect123!@#";
		private String driver="com.mysql.cj.jdbc.Driver";
	 */		
		@Autowired
		private DataSource dataSource;
	
		public void setDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		public List<Notice> getList(int page,String field,String query) throws SQLException, ClassNotFoundException{

			int start=page*10-10;
			
			String sql="SELECT * FROM (SELECT notice.*, @ROWNUM:=@ROWNUM+1 AS ROWNUM FROM notice, (SELECT @ROWNUM:=0) AS R) T WHERE "+field+" LIKE ? LIMIT ?,10;";
		

			//Class.forName(driver);
			//Connection con= DriverManager.getConnection(url,uid,pwd);
			Connection con=dataSource.getConnection();
			PreparedStatement st=con.prepareStatement(sql);
			st.setString(1,"%"+query+"%");
			st.setInt(2, start);
			ResultSet rs=st.executeQuery();
		
			List<Notice> list=new ArrayList<Notice>();
			while(rs.next()) {
				int id=rs.getInt("ID");
				String title=rs.getString("title");
				String writerId=rs.getString("writer_id");
				String content=rs.getString("content");
				Date regdate=rs.getDate("regdate");
				int hit=rs.getInt("hit");
				String files=rs.getString("files");
				
				Notice notice=new Notice(id,title,writerId,content,regdate,hit,files);
				
				list.add(notice);
			
			}
			rs.close();
			st.close();
			con.close();
		

		
	
			return list;
		}

		//Scalar
		public int getCount() throws ClassNotFoundException, SQLException {
			int count=0;
			String sql="SELECT COUNT(ID) COUNT FROM notice";
			

			//Class.forName(driver);
			//Connection con= DriverManager.getConnection(url,uid,pwd);
			Connection con=dataSource.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			
			if(rs.next()) {
				count=rs.getInt("count");
			}
			
			rs.close();
			st.close();
			con.close();

	
			return count;
		}
		
		public int insert(Notice notice) throws ClassNotFoundException, SQLException {
		
			String title=notice.getTitle();
			String writerId=notice.getWriterId();
			String content=notice.getContent();
			String files=notice.getFiles();
			
			
			String sql="INSERT INTO notice(title,writer_id,content) VALUE(?,?,?)";
			
			//Class.forName(driver);
			//Connection con= DriverManager.getConnection(url,uid,pwd);
			Connection con=dataSource.getConnection();
			PreparedStatement st=con.prepareStatement(sql);
			st.setString(1, title);
			st.setString(2, writerId);
			st.setString(3, content);

				
			int result=st.executeUpdate();		
				

			st.close();
			con.close();

			
			return result;
		}

		public int update(Notice notice) throws ClassNotFoundException, SQLException {
			String title=notice.getTitle();
			String content=notice.getContent();
			String files=notice.getFiles();
			int id=notice.getId();
			
			
			String sql="UPDATE NOTICE SET TITLE=?,CONTENT=?,FILES=? WHERE ID=?";
			
			//Class.forName(driver);
			//Connection con= DriverManager.getConnection(url,uid,pwd);
			Connection con=dataSource.getConnection();
			PreparedStatement st=con.prepareStatement(sql);
			st.setString(1, title);
			st.setString(2, content);
			st.setString(3, files);
			st.setInt(4, id);
				
			int result=st.executeUpdate();
				

			st.close();
			con.close();
				
			return result;

		}
		
		public int delet(int id) throws ClassNotFoundException, SQLException {
			
			
			String sql="DELETE NOTICE WHERE ID=?";
			

			//Class.forName(driver);
			//Connection con= DriverManager.getConnection(url,uid,pwd);
			Connection con=dataSource.getConnection();
			PreparedStatement st=con.prepareStatement(sql);
			st.setInt(1, id);

			int result=st.executeUpdate();

			st.close();
			con.close();
			
			return result;
		}


}
