package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class SignUpDAO {

	private final String JDBC_URL = "jdbc:mysql://localhost:3306/co_production?characterEncoding=utf-8&serverTimezone=JST";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";

	public boolean findByUser(User user) {

		//データベースへ接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//SELECT文を準備
			String sql = "SELECT USERID,USERNAME,USERPASS FROM USER WHERE USERNAME = ?";/*SQL文は小文字で入力してもOK*/
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, user.getName());

			//SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			//一致したユーザーが存在した場合（登録済みのID）、trueを返す
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		//ユーザーが見つからなかったら（未登録のID）、falseを返す
		return false;
	}

	public boolean create(User user) {
		//データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//INSERT文の準備
			String sql = "INSERT INTO USER(USERNAME,USERPASS) VALUES(?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			//INSERT文中の「?」に使用する値を設定しSQLを完成

			pStmt.setString(1, user.getName());//1つ目の「?」
			pStmt.setString(2, user.getPass());//2つ目の「?」

			//INSERTを実行（resultには追加された行数が代入される）
			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}