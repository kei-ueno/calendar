/*
 * 更新日：2020/12/18
 * 更新者：上野
 * 内容：コンストラクタ（String starttime, String endtime, String schedule）を追加
 */
package model;

import java.io.Serializable;

public class ScheduleBean implements Serializable{
	private int year;
	private int month;
	private int day;

	private int id;
	private int userid;
	private String scheduledate;
	private String starttime;
	private String endtime;
	private String schedule;
	private String schedulememo;

	public ScheduleBean(int userid, String scheduledate, String starttime, String endtime, String schedule,
			String schedulememo) {
		this.userid = userid;
		this.scheduledate = scheduledate;
		this.starttime = starttime;
		this.endtime = endtime;
		this.schedule = schedule;
		this.schedulememo = schedulememo;
	}

	public ScheduleBean(int userid, String scheduledate, String starttime, String schedule) {
		this.userid = userid;
		this.scheduledate = scheduledate;
		this.starttime = starttime;
		this.schedule = schedule;
	}

	public ScheduleBean(String starttime, String endtime, String schedule) {
		this.starttime = starttime;
		this.endtime = endtime;
		this.schedule = schedule;

	}

	public ScheduleBean(int year, int month, int day) {

		this.year = year;
		this.month = month;
		this.day = day;
	}
	public ScheduleBean(int id ,int year, int month, int day) {

		this.id = id;
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getScheduledate() {
		return scheduledate;
	}

	public void setScheduledate(String scheduledate) {
		this.scheduledate = scheduledate;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getSchedulememo() {
		return schedulememo;
	}

	public void setSchedulememo(String schedulememo) {
		this.schedulememo = schedulememo;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

}
