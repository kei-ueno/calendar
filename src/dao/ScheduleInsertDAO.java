//作成者：辻谷 2020/12/14

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.ScheduleBean;

public class ScheduleInsertDAO {

	private final String JDBC_URL = "jdbc:mysql://localhost:3306/co_production?characterEncoding=utf-8&serverTimezone=JST";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";

	public boolean create(ScheduleBean scheduleBean) {
		//データベースに接続に使用する情報
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {
			//insert文
			String sql = "INSERT INTO schedule(userid,scheduledate,starttime,endtime,schedule,schedulememo)"
					+ " VALUES(?,?,?,?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			//inset文中の「？」に使用する値を設定しSQLを完成する
			pStmt.setInt(1, scheduleBean.getUserid());
			pStmt.setString(2, scheduleBean.getScheduledate());
			pStmt.setString(3, scheduleBean.getStarttime());
			pStmt.setString(4, scheduleBean.getEndtime());
			pStmt.setString(5, scheduleBean.getSchedule());
			pStmt.setString(6, scheduleBean.getSchedulememo());

			//insert文を実行
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


//	public static void main(String[] args) {
//
//		ScheduleInsertDAO dao = new ScheduleInsertDAO();
//
//		 Schedulehyou sch = new Schedulehyou(
//				 1,//userid
//				 "2020/12/13",//scheduledate,
//				 "12:00",//starttime,
//				 "13:00",//endtime,
//				 "西尾デート",//schedule,
//				 "19時からディナー"//schedulememo
//				 );
//
//		 boolean doneInsert = dao.create(sch);
//		 System.out.println("doneInsert: " + doneInsert);
//
//	}//main()

}//class

/*
doneInsert: true
select * from schedule;
+----+--------+--------------+-----------+----------+-----------------+-------------------------+
| id | userid | scheduledate | starttime | endtime  | schedule        | schedulememo            |
+----+--------+--------------+-----------+----------+-----------------+-------------------------+
|  1 |      2 | 2020-12-13   | 12:00:00  | 13:00:00 | 西尾デート      | 19時からディナー        |
+----+--------+--------------+-----------+----------+-----------------+-------------------------+
*/