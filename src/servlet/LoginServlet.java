//ID namae

package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LoginLogic;
import model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*ログアウトしてindex.jspへフォワード*/

		//セッションの破棄
		HttpSession session = request.getSession();
		session.invalidate();

		//LoginServletにリダイレクト

		response.sendRedirect("/schedule/");


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*ログイン情報の取得とDAOからの情報取得*/

		//パラメーターを取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");

		//Userインスタンスを生成
		User user = new User(name, pass);
//		System.out.println("ユーザー情報"+user.getId()+user.getName()+user.getPass());

		LoginLogic loginLogic = new LoginLogic();
//		System.out.println("Logic"+user.getId()+user.getName()+user.getPass());

		user = loginLogic.execute(user);

		/*テスト用コード*/

//		System.out.println(user.getId()+user.getName()+user.getPass());
		//		User user = new User(1,"root","root");

		/*ユーザー情報セッションに保存。ログインresult画面へフォワード*/
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/loginResult.jsp");
		dispatcher.forward(request, response);
	}

}
