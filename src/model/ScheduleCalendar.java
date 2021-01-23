/*
 * 
 * 
 * 
 */
package model;

import java.util.Calendar;

public class ScheduleCalendar {
	int year, month,day,startWeek,beforeMonthlastDay,thisMonthlastDay,count;
	int[] calendarDay;
	
	private int nextMonthDay;
	public ScheduleCalendar(int year, int month, int day,int[] calendarDay, int count) {
		this.year = year;
		this.month = month;
		this.day = day;

		this.calendarDay = new int[42];  /* 最大で7日×6週 */
	}
	public int setDateArray(int year, int month, int day, int[] calendarDay, int count) {
		Calendar calendar = Calendar.getInstance();

		/* 今月が何曜日から開始されているか確認する */
		calendar.set(year, month, 1);
		this.startWeek = calendar.get(Calendar.DAY_OF_WEEK);


		/* 先月が何日までだったかを確認する */
		calendar.set(year, month, 0);
		this.beforeMonthlastDay = calendar.get(Calendar.DATE);


		/* 今月が何日までかを確認する */
		calendar.set(year, month + 1, 0);
		this.thisMonthlastDay = calendar.get(Calendar.DATE);


		/* 先月分の日付を格納する */
		for (int i = startWeek - 2; i >= 0; i--) {
			calendarDay[count++] = this.beforeMonthlastDay - i + 35;
		}

		/* 今月分の日付を格納する */
		for (int i = 1; i <= this.thisMonthlastDay; i++) {
			calendarDay[count++] = i;
		}

		/* 翌月分の日付を格納する */
		this.nextMonthDay = 1;
		while (count % 7 != 0) {
			calendarDay[count++] = 35 + this.nextMonthDay++;
		}

		return count;
	}
	public int getMonthLastDay(int year, int month, int day){

        Calendar calendar = Calendar.getInstance();

        /* 今月が何日までかを確認する */
        calendar.set(year, month + 1, 0);
        int thisMonthlastDay = calendar.get(Calendar.DATE);

        return thisMonthlastDay;
    }
	public int getYear() {
		return year;
	}
	public int getMonth() {
		return month;
	}
	public int getDay() {
		return day;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getStartWeek() {
		return startWeek;
	}
	public int getBeforeMonthlastDay() {
		return beforeMonthlastDay;
	}
	public int getThisMonthlastDay() {
		return thisMonthlastDay;
	}
	public int[] getCalendarDay() {
		
		return calendarDay;
	}


}
