
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO {

	private final String JDBC_URL = "jdbc:mysql://localhost:3306/co_production?charcterEncoding=UTF-8&serverTimezone=JST";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";

	public User findAdd(User user) {
//		System.out.println(user.getName() + user.getPass());

		//データベースに接続

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//SELECT文を準備
			String sql = "SELECT USERID,USERNAME,USERPASS FROM USER WHERE USERNAME=? AND USERPASS=? ";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, user.getName());
			pStmt.setString(2, user.getPass());

			/*　↓　testCode　↓　*/
			//System.out.println(user.getId());

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			rs.next();

			/*　↓　testCode　↓　*/
			//System.out.println("SELECT文実行成功" + rs.getInt("USERID"));

			user.setId(rs.getInt("USERID"));

		} catch (SQLException e) {
			e.printStackTrace();

			/*　↓　testCode　↓　*/
			//System.out.println("接続失敗");
			user = null;
			return user;
		}
		return user;

	}

}
