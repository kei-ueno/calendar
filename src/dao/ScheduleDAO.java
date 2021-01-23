/*
 *
 * 作成日：12/15
 * 作成者：岡本
 * 内容：スケジュールを取得するDAO
 *
 */
/*
 * 更新日：2020/12/18
 * 更新者：上野
 * 内容：findDayメソッド追加
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ScheduleBean;

public class ScheduleDAO {

	private final String JDBC_URL = "jdbc:mysql://localhost:3306/co_production?charcterEncoding=UTF-8&serverTimezone=JST";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";

	public List<ScheduleBean> findAdd() {
		List<ScheduleBean> scheduleList = new ArrayList<ScheduleBean>();

		//データベースに接続
		/*コード13-1と異なる書き方*/
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//SELECT文を準備
			String sql = "SELECT USERID,SCHEDULEDATE,STARTTIME,SCHEDULE FROM SCHEDULE ORDER BY SCHEDULEDATE";/*SQL文は小文字で入力してもOK*/
			PreparedStatement pStmt = conn.prepareStatement(sql);

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int userid = rs.getInt("USERID");
				String scheduledate = rs.getString("SCHEDULEDATE");
				String starttime = rs.getString("STARTTIME");
				String schedule = rs.getString("SCHEDULE");

				ScheduleBean scheduleBean = new ScheduleBean(userid, scheduledate, starttime, schedule);
				scheduleList.add(scheduleBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return scheduleList;
	}

	public List<ScheduleBean> findByMonth(int id, int year, int month, int[] calendarDay, int count) {
		List<ScheduleBean> scheduleList = new ArrayList<ScheduleBean>();

		//データベースに接続
		/*コード13-1と異なる書き方*/
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//SELECT文を準備
			String sql = "SELECT * FROM schedule WHERE userid = ? and scheduledate = ? ORDER BY starttime";/*SQL文は小文字で入力してもOK*/
			PreparedStatement pStmt = conn.prepareStatement(sql);

			String startDateStr = year + "-" + (month + 1) + "-" + calendarDay[count];
			pStmt.setInt(1, id);
			pStmt.setString(2, startDateStr);

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				String starttime = rs.getString("starttime");
				String endtime = rs.getString("endtime");
				String schedule = rs.getString("schedule");
				String ScheduleId = rs.getString("id");
				//				System.out.println(starttime);
				//			System.out.println("エンド"+endtime);
				ScheduleBean scheduleBean = new ScheduleBean(starttime, endtime, schedule);
				scheduleBean.setId(Integer.parseInt(ScheduleId));
				scheduleList.add(scheduleBean);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			//			System.out.println("キャッチ");

			return scheduleList;
		}
		//		for (ScheduleBean schedule : scheduleList) {
		//		System.out.println(schedule.getStarttime());
		//		System.out.println(schedule.getEndtime());
		//				}
		return scheduleList;
	}

	public List<ScheduleBean> findByDay(int id, int year, int month, int day) {

		List<ScheduleBean> scheduleList = new ArrayList<ScheduleBean>();

		//データベースに接続
		//		コード13-1と異なる書き方
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//SELECT文を準備
			String sql = "SELECT * FROM schedule WHERE userid = ? and scheduledate = ? ORDER BY starttime";//SQL文は小文字で入力してもOK
			PreparedStatement pStmt = conn.prepareStatement(sql);

			String startDateStr = year + "-" + (month + 1) + "-" + day;
			pStmt.setInt(1, id);
			pStmt.setString(2, startDateStr);

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				String starttime = rs.getString("starttime");
				String endtime = rs.getString("endtime");
				String schedule = rs.getString("schedule");
				String ScheduleId = rs.getString("id");

				//				System.out.println(starttime);
				//			System.out.println("エンド"+endtime);
				ScheduleBean scheduleBean = new ScheduleBean(starttime, endtime, schedule);
				scheduleBean.setId(Integer.parseInt(ScheduleId));
				scheduleList.add(scheduleBean);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			//			System.out.println("キャッチ");

			return scheduleList;
		}

		return scheduleList;
	}

	public ScheduleBean  findById(int userId,ScheduleBean scheduleBean) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "SELECT * FROM schedule WHERE id = ?";
			 PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            rs.next();
            String scheduledate = rs.getString("scheduledate");
            String yearStr = scheduledate.substring(0, 4);
            String monthStr = scheduledate.substring(5, 7);
            String dayStr = scheduledate.substring(8, 10);

            scheduleBean.setYear(Integer.parseInt(yearStr));
            scheduleBean.setMonth(Integer.parseInt(monthStr) - 1);
            scheduleBean.setDay(Integer.parseInt(dayStr));

            scheduleBean.setStarttime(rs.getString("starttime"));
            scheduleBean.setEndtime(rs.getString("endtime"));
            scheduleBean.setSchedule(rs.getString("schedule"));
            scheduleBean.setSchedulememo(rs.getString("schedulememo"));



		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
		return  scheduleBean;
		// TODO 自動生成されたメソッド・スタブ

	}


	public List<ScheduleBean> findAll() {
		List<ScheduleBean> scheduleList = new ArrayList<ScheduleBean>();

		//データベースに接続
		/*コード13-1と異なる書き方*/
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//SELECT文を準備
			String sql = "SELECT USERID,SCHEDULEDATE,STARTTIME,ENDTIME,SCHEDULE,SCHEDULEMEMO FROM SCHEDULE ORDER BY SCHEDULEDATE";/*SQL文は小文字で入力してもOK*/
			PreparedStatement pStmt = conn.prepareStatement(sql);

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int userId = rs.getInt("USERID");
				String scheduleDate = rs.getString("SCHEDULEDATE");
				String srartTime = rs.getString("STARTTIME");
				String endTime = rs.getString("ENDTIME");
				String schedule = rs.getString("SCHEDULE");
				String schedulememo = rs.getString("SCHEDULEMEMO");

				ScheduleBean scheduleBean = new ScheduleBean(userId, scheduleDate, srartTime, endTime, schedule,
						schedulememo);
				scheduleList.add(scheduleBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return scheduleList;
	}

}

/*
 *USERID:1 SCHEDULEDATE:2020-12-14 STARTTIME:12:30:00
ENDTIME:15:30:00
SCHEDULE:test
SCHEDULEMEMO:メモテスト

 select * from schedule;
+----+--------+--------------+-----------+----------+----------+-----------------+
| id | userid | scheduledate | starttime | endtime  | schedule | schedulememo    |
+----+--------+--------------+-----------+----------+----------+-----------------+
|  1 |      1 | 2020-12-14   | 12:30:00  | 15:30:00 | test     | メモテスト      |
+----+--------+--------------+-----------+----------+----------+-----------------+
 *
 */
