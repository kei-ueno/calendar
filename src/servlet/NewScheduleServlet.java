//作成日 12/17   作成者 西尾 隼 辻谷 浩基

/*
 * 更新日	：2020/12/18
更新者	：上野
更新内容：@WebServlet("/newScheduleCalendar")→@WebServlet("/newScheduleServlet")
*/

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
import model.ScheduleInsertLogic;
import model.ScheduleLogic;
import model.SelectLogic;
import model.User;

/**
 * Servlet implementation class newScheduleCalendar
 */
@WebServlet("/NewScheduleServlet")
public class NewScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ScheduleBean scheduleBean = (ScheduleBean) session.getAttribute("scheduleBean");

		String inputYear = request.getParameter("YEAR");
		String inputMonth = request.getParameter("MONTH");
		String inputDay = request.getParameter("DAY");
		String param = request.getParameter("ID");
		int currentscheduleid;

		ScheduleLogic scheduleLogic = new ScheduleLogic();

		if (param != null) {
			try {
				currentscheduleid = Integer.parseInt(param);
			} catch (NumberFormatException e) {
				currentscheduleid = -1;
			}
			SelectLogic selectLogic = new SelectLogic();

			scheduleBean = selectLogic.findById(currentscheduleid, scheduleBean);
			session.setAttribute("scheduleBeanById", scheduleBean);
			scheduleBean = null;

		} else {
			scheduleBean = null;
			session.setAttribute("scheduleBeanById", scheduleBean);

		}

		int year = scheduleLogic.makeFlag(inputYear);
		int month = scheduleLogic.makeFlag(inputMonth);
		int day = scheduleLogic.makeFlag(inputDay);
		/* パラメータが指定されていない場合は本日の日付を設定 */

		if (year == -999 || month == -999 || day == -999) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DATE);
		}

		scheduleBean = new ScheduleBean(year, month, day);
		session.setAttribute("scheduleBean", scheduleBean);

		/*if (param == null || param.length() == 0) {
			currentscheduleid = -1;

			scheduleBean = null;
			session.setAttribute("scheduleBeanById", scheduleBean);

		} else {
			try {
				currentscheduleid = Integer.parseInt(param);
			} catch (NumberFormatException e) {
				currentscheduleid = -1;
			}
			SelectLogic selectLogic = new SelectLogic();

			scheduleBean = selectLogic.findById(currentscheduleid, scheduleBean);
			session.setAttribute("scheduleBeanById", scheduleBean);

		}*/

		//セッションスコープに保存(カレンダーの構造)
		//		ScheduleBean scheduleBean = new ScheduleBean(year, month, day);

		request.setCharacterEncoding("UTF-8");

		//セレクトロジックのインスタンス化
		SelectLogic selectLogic = new SelectLogic();

		//セレクトロジックの実行
		List<ScheduleBean> scheduleList = selectLogic.findAll();

		//セッションスコープにscheduleのデータを保存
		session.setAttribute("scheduleListAll", scheduleList);

		scheduleList = selectLogic.findByDay(user.getId(), year, month, day);
		session.setAttribute("scheduleListByDay", scheduleList);

		String path = "/WEB-INF/jsp/newSchedule.jsp";
		RequestDispatcher dis = request.getRequestDispatcher(path);
		dis.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		//リクエストパラメータに保存
		//ユーザーの情報
//		String useridstr = request.getParameter("userid");
		HttpSession session = request.getSession();
		User user =(User)session.getAttribute("user");
		int userid =user.getId();


		//テスト用デモデータ
//		String useridstr = "1";

		//年/月/日
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");
		String daystr = request.getParameter("day");

		String date = yearstr + "/" + monthstr + "/" + daystr;

		//開始時間
		String shour = request.getParameter("shour");
		String sminute = request.getParameter("sminute");

		String starttime = shour + ":" + sminute;

		//終了時間
		String ehour = request.getParameter("ehour");
		String eminute = request.getParameter("eminute");

		String endtime = ehour + ":" + eminute;

		//スケジュールのタイトル&メモ
		String title = request.getParameter("schedule");
		String memo = request.getParameter("schedulememo");



		//リクエストスコープに保存するインスタンス生成
		ScheduleBean scheduleBean = new ScheduleBean(userid, date, starttime, endtime, title, memo);

		//リクエストスコープにインスタンスを保存
		request.setAttribute("schedulehyou", scheduleBean);

		//DAOにinsert文を送信する
		ScheduleInsertLogic sil = new ScheduleInsertLogic();
		boolean judge = sil.execute(scheduleBean);

		if (judge) {
			request.setAttribute("judge", "予定がカレンダーに登録されました");

		} else {
			request.setAttribute("judge", "予定が登録されませんでした");
		}

		String path = "/WEB-INF/jsp/newSchedule.jsp";
		RequestDispatcher dis = request.getRequestDispatcher(path);
		dis.forward(request, response);
	}
}
