<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>スケジュール管理</title>
<link rel="stylesheet" href="css/login.css">
 <link rel="stylesheet" href="css/logomark.css">
  <script src="js/common.js"></script>
</head>
<body>
	<canvas id="starfield"></canvas>
	<section id="body">
		<div class="wrapper">
			<div class="logo">
				<img src="img/logomark.png" alt="ロゴ" id="logomark" class="logomark purun">
			</div>
			<div id="login">
			<h1>スケジュール管理</h1>
						<form action="/schedule/LoginServlet" method="post">
							ユーザー名： <input type="text" name="name"><br>
							パスワード： <input	type="password" name="pass"><br>
							<input type="submit" value="ログイン">
						</form>
						<a href="/schedule/SignUpServlet">新規会員登録</a>
						</div>
						<img src="img/astronaut.png" alt="宇宙飛行士" class="astronaut">
		</div>
	</section>
	<script src="js/space.js"></script>
 <script>
    // ID値「logomark」に対してCSSアニメ―ション「purun」を600ミリ秒の間隔を空けてループ再生
    looopAnimation("logomark", "purun", 600);
  </script>
</body>
</html>