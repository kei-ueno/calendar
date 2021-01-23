<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録</title>
    <link rel="stylesheet" href="css/signup.css">
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
				<div id="sign-up">
				<h1>新規登録</h1>
					<form action="/schedule/SignUpServlet" method="post">
						<p>ユーザーID：<input type="text" name="userName" required></p>
						<p>パスワード：<input type="password" name="userPass"></p>
						<p>
						<input type="submit" value="登録">
						<input type="reset" value="入力リセット">
						</p>
					</form>
				<p><a href="/schedule/">ログインへ戻る</a></p>
			</div>
			<img src="img/astronaut2.png" alt="宇宙飛行士2" class="astronaut">
 		</div>
		</section>
    <script src="js/space.js"></script>
    <script>
    // ID値「logomark」に対してCSSアニメ―ション「purun」を600ミリ秒の間隔を空けてループ再生
    looopAnimation("logomark", "purun", 600);
  </script>
</body>
</html>