package model;

public class ScheduleLogic {

	public int makeFlag(String param) {

		int flag = 0;

		if (param == null || param.length() == 0) {
			flag = -999;
		} else {
			try {
				flag = Integer.parseInt(param);
			} catch (NumberFormatException e) {
				flag = -999;
			}
		}

		return flag;
	}//makeFlag()

}
