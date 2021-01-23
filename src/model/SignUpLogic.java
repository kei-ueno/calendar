package model;

import dao.SignUpDAO;

public class SignUpLogic {

	public boolean execute(User user) {

		SignUpDAO dao = new SignUpDAO();
		boolean registered = dao.findByUser(user);

		if (!registered) {
			dao.create(user);
			return true;
		} else {
			return false;
		}
	}
}
