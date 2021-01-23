package model;

import java.util.Calendar;

public class CalendarSet {

	int setDateArray(int year, int month, int day, int[] calendarDay, int count) {
		Calendar calendar = Calendar.getInstance();

		/* 今月が何曜日から開始されているか確認する */
		calendar.set(year, month, 1);
		int startWeek = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("今月の曜日は" + startWeek + "から");

		/* 先月が何日までだったかを確認する */
		calendar.set(year, month, 0);
		int beforeMonthlastDay = calendar.get(Calendar.DATE);
		System.out.println("先月は" + beforeMonthlastDay + "日まで");

		/* 今月が何日までかを確認する */
		calendar.set(year, month + 1, 0);
		int thisMonthlastDay = calendar.get(Calendar.DATE);
		System.out.println("今月は" + thisMonthlastDay + "日まで\r\n");

		/* 先月分の日付を格納する */
		for (int i = startWeek - 2; i >= 0; i--) {
			calendarDay[count++] = beforeMonthlastDay - i + 35;
		}

		/* 今月分の日付を格納する */
		for (int i = 1; i <= thisMonthlastDay; i++) {
			calendarDay[count++] = i;
		}

		/* 翌月分の日付を格納する */
		int nextMonthDay = 1;
		while (count % 7 != 0) {
			calendarDay[count++] = 35 + nextMonthDay++;
		}

		return count;
	}
	
	 protected int getMonthLastDay(int year, int month, int day){

	        Calendar calendar = Calendar.getInstance();

	        /* 今月が何日までかを確認する */
	        calendar.set(year, month + 1, 0);
	        int thisMonthlastDay = calendar.get(Calendar.DATE);

	        return thisMonthlastDay;
	    }
}
