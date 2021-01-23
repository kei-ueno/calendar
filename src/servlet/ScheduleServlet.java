//作成日 12/17   作成者 西尾 隼 辻谷 浩基

package servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ScheduleBean;
import model.ScheduleCalendar;
import model.SelectLogic;
import model.User;

@WebServlet(description = "Maine-newScheduleのコントローラー", urlPatterns = { "/ScheduleServlet" })
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		int[] calendarDay;
		int count;

		int year;
		int month;
		int day = 1;

		calendarDay = new int[42]; /* 最大で7日×6週 */
		count = 0;

		String param = request.getParameter("YEAR");
		if (param == null || param.length() == 0) {
			year = -999;
		} else {
			try {
				year = Integer.parseInt(param);
			} catch (NumberFormatException e) {
				year = -999;
			}
		}

		param = request.getParameter("MONTH");
		if (param == null || param.length() == 0) {
			month = -999;
		} else {
			try {
				month = Integer.parseInt(param);
			} catch (NumberFormatException e) {
				month = -999;
			}
		}
		if (year == -999 || month == -999) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DATE);
		} else {
			if (month == 12) {
				month = 0;
				year++;
			}

			if (month == -1) {
				month = 11;
				year--;
			}
		}

		ScheduleCalendar scheduleCalendar = new ScheduleCalendar(year, month, day, calendarDay, count);

		request.setCharacterEncoding("UTF-8");

		//リクエストパラメータに保存
		//ユーザーの情報
		HttpSession session = request.getSession();

		User user = (User)session.getAttribute("user");

		//テスト用デモデータ
//		String useridstr = "1";

		//年/月/日
//		String yearstr = request.getParameter("year");
//		String monthstr = request.getParameter("month");
//		String daystr = request.getParameter("day");

//		String date = yearstr + "/" + monthstr + "/" + daystr;

		//開始時間
//		String shour = request.getParameter("shour");
//		String sminute = request.getParameter("sminute");

//		String starttime = shour + ":" + sminute;

		//終了時間
//		String ehour = request.getParameter("ehour");
//		String eminute = request.getParameter("eminute");

//		String endtime = ehour + ":" + eminute;

		//スケジュールのタイトル&メモ
//		String title = request.getParameter("schedule");
//		String memo = request.getParameter("schedulememo");

		int userid = user.getId();

		//セレクトロジックのインスタンス化
		SelectLogic csl = new SelectLogic();

		//セレクトロジックの実行
		List<ScheduleBean> scheduleList = csl.findAdd();
		ScheduleBean scheduleBean = new ScheduleBean(year, month, day);

		//セッションスコープにscheduleのデータを保存

		session.setAttribute("scheduleBean", scheduleBean);
		session.setAttribute("scheduleList", scheduleList);
		session.setAttribute("scheduleCalendar", scheduleCalendar);

		int currentscheduleid;
//		ScheduleLogic scheduleLogic = new ScheduleLogic();

		if (param == null || param.length() == 0) {
			currentscheduleid = -1;
		} else {
			try {
				currentscheduleid = Integer.parseInt(param);
			} catch (NumberFormatException e) {
				currentscheduleid = -1;
			}
			SelectLogic selectLogic = new SelectLogic();
			scheduleBean = selectLogic.findById(currentscheduleid, scheduleBean);
			session.setAttribute("scheduleBeanById", scheduleBean);}

		SelectLogic selectLogic = new SelectLogic();

		scheduleList = selectLogic.findAll();

		//セッションスコープにscheduleのデータを保存
		session.setAttribute("scheduleListAll", scheduleList);

		scheduleList = selectLogic.findByDay(userid, year, month, day);
		session.setAttribute("scheduleListByDay", scheduleList);


		String path = "/WEB-INF/jsp/main.jsp";
		RequestDispatcher dis = request.getRequestDispatcher(path);
		dis.forward(request, response);
	}

}
