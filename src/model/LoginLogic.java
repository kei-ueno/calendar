package model;

import dao.UserDAO;

public class LoginLogic {

	public User execute(User user) {
		UserDAO dao = new UserDAO();
		User loginUser = dao.findAdd(user);

		return loginUser;

	}

}
