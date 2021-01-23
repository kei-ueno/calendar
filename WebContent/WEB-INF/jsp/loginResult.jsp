<!--

制作者	：岡本
制作日	：2020/12/17
内容	：ユーザーのログイン結果用ビュー
 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="model.User"%>
<%
	//セッションスコープからユーザー情報を取得
User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スケジュール管理</title>
<link rel="stylesheet" href="css/loginresult.css">
<link rel="stylesheet" href="css/logomark.css">
<link rel="stylesheet" href="css/eva.css">
<link rel="stylesheet" href="css/failure.css">
<script src="js/common.js"></script>
</head>
<body>
	<canvas id="starfield"></canvas>
	<section id="body">
		<div class="wrapper">
			<%
				if (user != null) {
			%>
			<div class="logo">
				<img src="img/logomark.png" alt="ロゴ" id="logomark"
					class="logomark purun">
			</div>

			<div class="seikou">
			<p>ログインに成功しました</p>
			<p>ようこそ<%=user.getName()%>さん</p>
			</div>

			<a id="ao" href="/schedule/ScheduleServlet" class="all">カレンダーへ</a>
			<%
				} else {
			%>
			<div class="logofailure">
				<img src="img/logomark.png" alt="ロゴ" id="logomark"
					class="logomark purun">
			</div>

			<div class="container">
				<section class="section">
					<div class="section__wrapper">

						<input type="checkbox" id="eva">
						<div class="eva__wrapper">
							<label class="eva" for="eva" id="se-play">
								<!-- ここのエラーはlabelの本来の使い方でなないので出る -->
								<div class="eva__top">
									<div class="eva__patition eva__line--left">
										<div class="eva__line--top1"></div>
										<div class="eva__line--top2"></div>
									</div>
									<div class="eva__patition eva__line--right">
										<div class="eva__line--top1"></div>
										<div class="eva__line--top2"></div>
									</div>
									<div class="eva__modifire"></div>
									<div class="eva__muzzleBord">
										<div class="eva__muzzle"></div>
										<div class="eva__muzzle"></div>
									</div>
									<div class="eva__box">
										<p class="eva__number">06</p>
										<p class="eva__question">ログイン失敗!!<br>
										ここをクリック</p>
									</div>
								</div>
							</label>

							<div class="eva__textarea">
								<p class="eva__text"><a href="/schedule/">再出撃</a></p>
								<div class="eva__textmodi"></div>
							</div>

							<div class="eva__bottom">
								<div class="eva__line--under"></div>
								<div class="eva__line--under"></div>
								<div class="eva__light eva__light--left"></div>
								<div class="eva__light eva__light--right"></div>
								<div class="eva__undermodi"></div>
							</div>

							<audio id="se" src="audio/Warning-Siren01-1.mp3" controls></audio>
						</div>
					</div>
				</section>
			</div>
			<%
				}
			%>
		</div>
	</section>
	<script src="js/space.js"></script>
	<script>
    // ID値「logomark」に対してCSSアニメ―ション「purun」を
    //600ミリ秒の間隔を空けてループ再生
    looopAnimation("logomark", "purun", 600);
 	</script>
	<script>
        const bgm1 = document.querySelector("#se");       // <audio>
		const btn  = document.querySelector("#se-play");
        btn.addEventListener("click", ()=>{
  			// 再生する
  			bgm1.play();
		});
	</script>
</body>
</html>