<!--
制作者	：上野
制作日	：2020/12/10
内容	：スケジュールの登録ビュー
		　

-->


<!--

作成日 12/17   作成者 西尾 隼 辻谷 浩基

-->

<!--

更新日	：2020/12/18
更新者	：上野
内容	：リンク切れの対処

-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="model.ScheduleBean"%>
<%@ page import="model.SelectLogic"%>
<%@page import="model.ScheduleCalendar"%>
<%@page import="model.User"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.List"%>
<%
	//リクエストスコープをインスタンスから取得

//リクエストスコープから取得
String judge = (String) request.getAttribute("judge");

//セッションスコープからuserの情報を取得

//セッションスコープからscheduleListのデータを取得
List<ScheduleBean> scheduleListAll = (List<ScheduleBean>) session.getAttribute("scheduleListAll");
List<ScheduleBean> scheduleListByDay = (List<ScheduleBean>) session.getAttribute("scheduleListByDay");
ScheduleCalendar sc = (ScheduleCalendar) session.getAttribute("scheduleCalendar");
ScheduleBean scheduleBean = (ScheduleBean) session.getAttribute("scheduleBean");
ScheduleBean scheduleBeanById = (ScheduleBean) session.getAttribute("scheduleBeanById");
User user = (User) session.getAttribute("user");
%>
<%!protected int getMonthLastDay(int year, int month, int day) {

		Calendar calendar = Calendar.getInstance();

		/* 今月が何日までかを確認する */
		calendar.set(year, month + 1, 0);
		int thisMonthlastDay = calendar.get(Calendar.DATE);

		return thisMonthlastDay;
	}%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/calendarStyle.css">
<title>スケジュール登録</title>
</head>
<body>
	<p>
		スケジュール登録&nbsp;&nbsp;[<a
			href="/schedule/ScheduleServlet?YEAR=<%=sc.getYear()%>&MONTH=<%=sc.getMonth()%>">カレンダーへ戻る</a>]
		&nbsp;&nbsp;<a href="/schedule/LoginServlet">[ログアウト]</a>

		<%
			String[] scheduleArray = new String[49];
		int[] widthArray = new int[49];

		for (int i = 0; i < 49; i++) {
			scheduleArray[i] = "";
			widthArray[i] = 0;
		}

		SelectLogic sl = new SelectLogic();
		scheduleListByDay = sl.findByDay(user.getId(), scheduleBean.getYear(), scheduleBean.getMonth(), scheduleBean.getDay());

		for (ScheduleBean rs : scheduleListByDay) {
			String starttime = rs.getStarttime();
			String endtime = rs.getEndtime();
			String schedule = rs.getSchedule();

			if (starttime == null || endtime == null) {
				widthArray[0] = 1;
				scheduleArray[0] = scheduleArray[0] + schedule + "<br>";
			} else {
				String startTimeStr = starttime.substring(0, 2);
				String startMinuteStr = starttime.substring(3, 5);

				int startTimeNum = Integer.parseInt(startTimeStr);
				int index = startTimeNum * 2 + 1;
				if (startMinuteStr.equals("30")) {
			index++;
				}

				if (widthArray[index] == 0) {
			/* 既にデータが入っていた場合は異常な状態なので無視する */

			String endTimeStr = endtime.substring(0, 2);
			String endMinuteStr = endtime.substring(3, 5);

			int endTimeNum = Integer.parseInt(endTimeStr);
			int width = (endTimeNum - startTimeNum) * 2;

			if (startMinuteStr.equals("30")) {
				width--;
			}

			if (endMinuteStr.equals("30")) {
				width++;
			}

			StringBuffer sbSchedule = new StringBuffer();
			sbSchedule.append(startTimeStr);
			sbSchedule.append(":");
			sbSchedule.append(startMinuteStr);
			sbSchedule.append("-");
			sbSchedule.append(endTimeStr);
			sbSchedule.append(":");
			sbSchedule.append(endMinuteStr);
			sbSchedule.append(" ");
			sbSchedule.append("<a href=\"/schedule/NewScheduleServlet?ID=");
			sbSchedule.append(rs.getId());
			sbSchedule.append("&YEAR=");
			sbSchedule.append(scheduleBean.getYear());
			sbSchedule.append("&MONTH=");
			sbSchedule.append(scheduleBean.getMonth());
			sbSchedule.append("&DAY=");
			sbSchedule.append(scheduleBean.getDay());
			sbSchedule.append("\">");
			sbSchedule.append(schedule);
			sbSchedule.append("</a>");

			scheduleArray[index] = new String(sbSchedule);
			widthArray[index] = width;

			/* 同じスケジュールの先頭以外の箇所には「-1」を設定 */
			for (int i = 1; i < width; i++) {
				widthArray[index + i] = -1;
			}
				}
			}
		}
		%>

	<div id="contents">
		<div id="left">
			<table class="sche">
				<tr>
					<td class="top" style="width: 80px">時刻</td>
					<td class="top" style="width: 300px">予定</td>
				</tr>

				<tr>
					<td class="timeb">未定</td>
					<td class="contentsb">
						<%
							if (widthArray[0] == 1) {
						%> <%=scheduleArray[0]%> <%
 	}
 %>
					</td>
				</tr>
				<%
					for (int i = 1; i < 49; i++) {
					if (i % 2 == 1) {
				%>
				<tr>
					<td class="time"><%=i / 2%>:00</td>

					<%
						} else {
					%>
				</tr>
				<tr>
					<td class="timeb"></td>
					<%
						}
					%>
					<%
						if (widthArray[i] != 0) {
						if (widthArray[i] != -1) {
					%>
					<td class="ex" rowspan="<%=widthArray[i]%>"><%=scheduleArray[i]%>
						<%
							}
						%> <%
 	} else {
 %> <%
 	if (i % 2 == 1) {
 %>
					<td class="contents">
						<%
							} else {
						%>

					<td class="contentsb">
						<%
							}
						%> <%
 	}
 %>
					</td>
				</tr>
				<%
					}
				%>

			</table>
		</div>
		<div id="right">
			<form method="post" action="/schedule/NewScheduleServlet">
			<div class="logo">
			<img src="img/logo4.png" alt="ロゴ" id="logomark4">
			</div>
				<table>
					<tr>
						<td nowrap>日付</td>
						<td><select name="year">
								<%
									for (int i = sc.getYear(); i <= sc.getYear() + 3; i++) {
								%>
								<option value="<%=i%>" <%if (i == scheduleBean.getYear()) {%>
									selected <%}%>><%=i%>年<%
									} //for
								%>

						</select> <select name="month">
								<%
									for (int i = 1; i <= 12; i++) {
								%>
								<option value="<%=i%>"
									<%if (i == (scheduleBean.getMonth() + 1)) {%> selected <%}%>><%=i%>月
									<%
									}
								%>

						</select> <select name="day">
								<%
									int monthLastDay = getMonthLastDay(scheduleBean.getYear(), scheduleBean.getMonth(), scheduleBean.getDay());
								for (int i = 1; i <= monthLastDay; i++) {
								%>
								<option value="<%=i%>" <%if (i == scheduleBean.getDay()) {%>
									selected <%}%>>
									<%=i%>日
									<%
										}
									%>

						</select></td>
					</tr>
					<tr>
						<td nowrap>時刻</td>
						<td><select name="shour"><option value="" selected>--時
									<%
							for (int i = 0; i <= 23; i++) {
						%>

								<option value="<%=i%>"><%=i%>時
									<%
																	}
																%>
								</select> <select name="sminute">
								<option value="0">00分
								<option value="30">30分
						</select> -- <select name="ehour">
								<option value="" selected>--時
									<%
									for (int i = 0; i <= 23; i++) {
								%>

								<option value="<%=i%>"><%=i%>時
									<%
																	}
																%>

						</select> <select name="eminute">
								<option value="0">00分
								<option value="30">30分
						</select></td>
					</tr>
					<tr>
						<td nowrap>予定</td>
						<td><input type="text" name="schedule" value="" size="30"
							maxlength="100"></td>
					</tr>
					<tr>
						<td valign="top" nowrap="nowrap">メモ</td>
						<td><textarea name="schedulememo" cols="30" rows="10"
								wrap="virtual"></textarea></td>
					</tr>
				</table>
				<p>
					<input type="submit" name="register" value="登録する"> <input
						type="reset" value="入力し直す">
				</p>
			</form>
			<%
				if (judge == null) {
				;
			} else {
			%>
			<p><%=judge%></p>
			<%
				}
			%>
			<%
				if (scheduleBeanById != null) {
			%>

			<table class="view">
				<tr>
					<td class="left">日付</td>
					<td><%=scheduleBeanById.getYear()%>年 <%=scheduleBeanById.getMonth() + 1%>月
						<%=scheduleBeanById.getDay()%>日</td>
				</tr>
				<tr>
					<td class="left">時間</td>
					<td>
						<%
							if (scheduleBeanById.getStarttime() == null) {
						%> 未定 <%
							} else {
						%> <%=scheduleBeanById.getStarttime().substring(0, 5)%> － <%=scheduleBeanById.getEndtime().substring(0, 5)%>
						<%
							}
						%>

					</td>
				</tr>

				<tr>
					<td class="left">スケジュール</td>
					<td><%=scheduleBeanById.getSchedule()%></td>
				</tr>
				<tr>
					<td class="left" style="height: 150px;">メモ</td>
					<td>
						<%
							scheduleBeanById.setSchedulememo(scheduleBeanById.getSchedulememo().replaceAll("\r\n", "<br>"));
						%> <%=scheduleBeanById.getSchedulememo()%>
					</td>
				</tr>
			</table>
			<%
				}
			%>

		</div>
	</div>
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>



