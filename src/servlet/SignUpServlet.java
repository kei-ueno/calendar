package servlet;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.SignUpLogic;
import model.User;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/signUp.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("userName");
		String pass = request.getParameter("userPass");

		//名前のパターンの生成(任意の文字1文字以上20文字以下)
		Pattern namePattern = Pattern.compile("^.{1,20}$");
		//パスワードのパターンの生成（4文字以上20文字以下）
		Pattern passPattern = Pattern.compile("^[0-9a-zA-Z]{4,20}$");
		System.out.println(name + pass);
		//リクエストパラメータをチェック
		String errorMsg = "";
		if (name == null || name.length() == 0) {
			errorMsg += "名前が入力されていません<br>";
		} else if (namePattern.matcher(name).matches()) {
			errorMsg += "";
		} else {
			errorMsg += "名前が1～20文字で入力されていません<br>";
		}

		if (pass == null || pass.length() == 0) {
			errorMsg += "パスワードが入力されていません<br>";
		} else if (passPattern.matcher(pass).matches()) {
			errorMsg += "";
		} else {
			errorMsg += "パスワードが半角英数4～20文字で入力されていません<br>";
		}

		//エラーメッセージをリクエストスコープに保存
		request.setAttribute("errorMsg", errorMsg);

		User user = new User(name, pass);

		//登録するユーザーの情報を設定
		SignUpLogic sul = new SignUpLogic();
		boolean registration = sul.execute(user);

		//新規登録出来た場合（nameが登録済みではなかった）
		if (registration) {

			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/signUpResult.jsp");
			dispatcher.forward(request, response);

		} else {//登録済みだった場合
			//被ってしまっている時

			String registered="登録済みのIDです";

			//登録済みメッセージをリクエストスコープに保存
			request.setAttribute("registered", registered);

		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/signUpResult.jsp");
		dispatcher.forward(request, response);
	}

}
