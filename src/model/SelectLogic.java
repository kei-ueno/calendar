package model;

import java.util.List;

import dao.ScheduleDAO;

public class SelectLogic {

	public List<ScheduleBean> findAll() {

		ScheduleDAO dao = new ScheduleDAO();
		List<ScheduleBean> scheduleList = dao.findAll();

		return scheduleList;
	}

	public List<ScheduleBean> findAdd() {

		ScheduleDAO dao = new ScheduleDAO();
		List<ScheduleBean> scheduleList = dao.findAdd();

		return scheduleList;
	}

	public List<ScheduleBean> findByMonth(int id, int year, int month, int[] calendarDay, int count) {

		ScheduleDAO dao = new ScheduleDAO();

		List<ScheduleBean> scheduleList = dao.findByMonth(id, year, month, calendarDay, count);

		return scheduleList;

	}

	public List<ScheduleBean> findByDay(int id, int year, int month, int day) {

		ScheduleDAO dao = new ScheduleDAO();

		List<ScheduleBean> scheduleList = dao.findByDay(id, year, month, day);

		return scheduleList;

	}

	public ScheduleBean findById(int userId, ScheduleBean scheduleBean) {
		ScheduleDAO dao = new ScheduleDAO();

		scheduleBean = dao.findById(userId, scheduleBean);

		return scheduleBean;
	}
}
