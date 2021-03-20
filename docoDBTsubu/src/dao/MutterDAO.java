package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class MutterDAO {
//データベース接続に使用する情報
	private final String JDBC_URL ="jdbc:h2:tcp://localhost/~/example";
	private final String DB_USER ="sa";
	private final String DB_PASS="";
	
	
	public List<Mutter> findALL(){
		List<Mutter> mutterList = new ArrayList<>();
		//データベース接続
		try(Connection conn =DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			String sql ="SELECT ID,NAME,TEXT FROM MUTTER ORDER BY ID DESC";
			PreparedStatement pStmt =conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			//selectを実行
			while(rs.next()) {
				int id = rs.getInt("ID");
				String userName = rs.getString("NAME");
				String text = rs.getString("TEXT");
				Mutter mutter = new Mutter(id,userName,text);
				mutterList.add(mutter);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return mutterList;
	}
	public boolean create(Mutter mutter) {
		try(Connection conn =DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			//INSERT文の準備
			String sql="INSERT INTO MUTTER(NAME,TEXT) VALUES(?,?)";
			PreparedStatement pStmt =conn.prepareStatement(sql);
			//insert文中の？に使用する値をせってしSQLをつく
		   pStmt.setString(1,mutter.getUserName());
		   pStmt.setString(2,mutter.getText());	   
		   //INSERT文を実行
		   int result =pStmt.executeUpdate();
		   if(result !=1) {
			   return false;
		   }
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}