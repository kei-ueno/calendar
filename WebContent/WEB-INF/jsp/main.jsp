<!--

制作者	：上野
制作日	：2020/12/10
内容	：カレンダーページのビュー。
		　カレンダーの生成と各日付へのリンク
		　ログアウトリンク
		　各予定のリンク[未実装]

スコープ：session"scheduleCalendar","scheduleList"

 -->

<!--
作成日 12/17   作成者 西尾 隼 辻谷 浩基
-->

<!--

変更日	：2020/12/18
更新者	：上野
更新内容：リンク切れの対処
-->


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.io.*"%>
<%@ page import="model.ScheduleCalendar"%>
<%@ page import="model.ScheduleBean"%>
<%@ page import="model.SelectLogic"%>
<%@ page import="model.User"%>

<%@ page import="java.util.List"%>
<%

ScheduleCalendar sc = (ScheduleCalendar) session.getAttribute("scheduleCalendar");

//セッションスコープからscheduleListのデータを取得
List<ScheduleBean> scheduleList = (List<ScheduleBean>) session.getAttribute("scheduleList");
User user = (User) session.getAttribute("user");
%>
<!-- ↓↓↓ 以下HTML ↓↓↓ -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スケジュール管理</title>

<style>
table {
	border: 1px solid #a9a9a9;
	width: 80%;
	padding: 0px;
	margin: 0 auto;
	border-collapse: collapse;
}

td {
	width: 12%;
	border-top: 1px solid #a9a9a9;
	border-left: 1px solid #a9a9a9;
	vertical-align: top;
	margin: 0px;
	padding: 2px;
}

td.week {
	background-color: #f0f8ff;
	text-align: center;
}

td.day {
	background-color: #f5f5f5;
	text-align: right;
	font-size: 0.75em;
}

td.otherday {
	background-color: #f5f5f5;
	color: #d3d3d3;
	text-align: right;
	font-size: 0.75em;
}

td.sche {
	background-color: #fffffff;
	text-align: left;
	height: 80px;
}

img {
	border: 0px;
}

.logo img{
	display:block;
	margin:0 auto;
	width:20%;
	height:20%;
}

p {
	font-size: 1.125em;
	text-align:center;
}

/* 1列目（日曜日）のスタイル */
/*日曜日の「日」*/
tr:nth-child(1) td:nth-child(1){
  background-color:#fef4f4;
  color:#f00;
}

/*日曜日の「数字」*/
tr:nth-child(2n) td:nth-of-type(1){
  background-color:#fef4f4;
  color:#f00;
}


/* 7列目（土曜日）のスタイル */
/*土曜日の「土」*/
tr:nth-child(1) td:nth-child(7){
  background-color:#c1e4e9;
  color:#00f;
}

/*土曜日の「数字」*/
tr:nth-child(2n) td:nth-of-type(7){
  background-color:#c1e4e9;
  color:#00f;
}


</style>

</head>
<body>

	<div class="logo">

				<img src="img/logo4.png" alt="ロゴ" id="logomark4">
	</div>

	<p>
	<%
		sc.setCount(sc.setDateArray(sc.getYear(), sc.getMonth(), sc.getDay(), sc.getCalendarDay(), sc.getCount()));
	%>
	<%=createMonthLink(sc.getYear(), sc.getMonth())%>
	</p>


	<table>

		<tr>
			<td class="week">日</td>
			<td class="week">月</td>
			<td class="week">火</td>
			<td class="week">水</td>
			<td class="week">木</td>
			<td class="week">金</td>
			<td class="week">土</td>
		</tr>
		<%
			int weekCount = sc.getCount() / 7;
		int[] calendarDay = sc.getCalendarDay();

		for (int i = 0; i < weekCount; i++) {
		%>
		<tr>
			<%
				for (int j = i * 7; j < i * 7 + 7; j++) {
				if (calendarDay[j] > 35) {
			%>

			<td class="otherday"><%=calendarDay[j] - 35%> <%
 	} else {
 %>
			<td class="day"><%=calendarDay[j]%> <%
 	}
 %></td>
			<%
				}
			%>
		</tr>
		<%=createScheduleStr(user.getId(), sc.getYear(), sc.getMonth(), i * 7, sc.getCalendarDay(), scheduleList)%>

		<%
			}
		%>


	</table>
<%--  	<%
		for (ScheduleBean schedulehyou : scheduleList) {
	%>
	<p>
		<%=schedulehyou.getUserid()%>
		<%=schedulehyou.getScheduledate()%>
		<%=schedulehyou.getStarttime()%>
		<%=schedulehyou.getSchedule()%>

	</p>
	<%
		}
	%> --%>

<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>




<!-- 以下メソッド -->



<%!protected String createScheduleStr(int userid, int year, int month, int startDayNo, int[] calendarDay,
			List<ScheduleBean> scheduleList) {
		StringBuffer sb = new StringBuffer();

		sb.append("<tr>");

		for (int i = startDayNo; i < startDayNo + 7; i++) {
			if (calendarDay[i] > 35) {
				/* 前月及び翌月の箇所にはアイコンは表示しない */
				sb.append("<td class=\"sche\"></td>");
			} else {
				sb.append("<td class=\"sche\">");
				sb.append("<a href=\"/schedule/NewScheduleServlet");

				/* パラメータの作成 */
				sb.append("?YEAR=");
				sb.append(year);
				sb.append("&MONTH=");
				sb.append(month);
				sb.append("&DAY=");
				sb.append(calendarDay[i]);

				sb.append("\">");
				sb.append("<img src=\"img/pencil.png\" width=\"14\" height=\"16\">");
/* 				sb.append(year);
				sb.append(month);
				sb.append(calendarDay[i]); */
				sb.append("</a><br>");
				sb.append("<span class=\"small\">");

				SelectLogic sl = new SelectLogic();
				List<ScheduleBean> scList = sl.findByMonth(userid, year, month, calendarDay, i);

				if (sl != null) {
					for (ScheduleBean schedule : scList) {
						int id = schedule.getId();
						String starttime = schedule.getStarttime();
						String endtime = schedule.getEndtime();
						String scheduleTitle = schedule.getSchedule();

						if (starttime == null || endtime == null) {
							sb.append("* ");
						} else {
							sb.append(starttime.substring(0, 5));
							sb.append("-");
							sb.append(endtime.substring(0, 5));
							sb.append(" ");
						}
						sb.append("<a href=\"/schedule/NewScheduleServlet?ID=");
						sb.append(schedule.getId());
						sb.append("&YEAR=");
						sb.append(year);
						sb.append("&MONTH=");
						sb.append(month);
						sb.append("&DAY=");
						sb.append(calendarDay[i]);
						sb.append("\">");
						sb.append(scheduleTitle);
						sb.append("</a><br>");
					}
				}

			}
		}
		return (new String(sb));
	}%>

<%!protected String createMonthLink(int year, int month) {
		StringBuffer sb = new StringBuffer();

		sb.append("<p>");

		sb.append("<a href=\"/schedule/ScheduleServlet?YEAR=");
		sb.append(year);
		sb.append("&MONTH=");
		sb.append(month - 1);
		sb.append("\"><span class=\"small\">前月</span></a>  ");

		sb.append(year);
		sb.append("年");
		sb.append(month + 1);
		sb.append("月  ");

		sb.append("<a href=\"/schedule/ScheduleServlet?YEAR=");
		sb.append(year);
		sb.append("&MONTH=");
		sb.append(month + 1);
		sb.append("\"><span class=\"small\">翌月</span></a>");
		sb.append("&nbsp;&nbsp;<a href=\"/schedule/LoginServlet\">[ログアウト]</a>");
		sb.append("</p>");

		return (new String(sb));
	}%>