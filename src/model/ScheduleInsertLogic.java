package model;

import dao.ScheduleInsertDAO;

public class ScheduleInsertLogic {
	public boolean execute(ScheduleBean scheduleBean) {
		ScheduleInsertDAO dao = new ScheduleInsertDAO();
		boolean judge = dao.create(scheduleBean);
		return judge;

	}
}
