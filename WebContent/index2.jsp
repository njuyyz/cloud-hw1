
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="model.Conversation"%>
<%@ page import="model.Video"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<script type="text/javascript" src="jwplayer/jwplayer.js"></script>
<script type="text/javascript">
	document.createElement('video');
	document.createElement('audio');
	document.createElement('track');

	$(document).ready(function() {
		$("#message").fadeIn("slow");
		$("#message").fadeOut("slow");
	});
</script>
<style type="text/css">
#message {
	font-family: Arial, Helvetica, sans-serif;
	position: fixed;
	top: 0px;
	left: 0px;
	width: 100%;
	z-index: 105;
	text-align: center;
	font-weight: bold;
	font-size: 100%;
	color: white;
	padding: 10px 0px 10px 0px;
	background-color: #AEB404;
}

#message span {
	text-align: center;
	width: 95%;
	float: left;
}

div.upload {
	width: 200px;
	height: 50px;
	background: url(new_conversation_btn_false.png);
	background-size: 200px 50px;
	overflow: hidden;
}

div.upload input {
	display: block !important;
	width: 157px !important;
	height: 57px !important;
	opacity: 0 !important;
	overflow: hidden !important;
}
</style>
<link href="//vjs.zencdn.net/4.4/video-js.css" rel="stylesheet">
<script src="//vjs.zencdn.net/4.4/video.js"></script>


<title>Carousel Template for Bootstrap</title>

<!-- Bootstrap core CSS -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
      <![endif]-->

<!-- Custom styles for this template -->
<link href="carousel.css" rel="stylesheet">
</head>
<!-- NAVBAR
  ================================================== -->
<body>
	<%
		String status = "";
			if (request.getAttribute("UploadStatus") != null) {
			 status = (String) request.getAttribute("UploadStatus");
	%>
	<div id='message' style="display: none;">
		<span><%=status%></span>
	</div>
	<%
		}
	%>

	<div class="navbar-wrapper">
		<div class="container">

			<div class="navbar navbar-inverse navbar-static-top"
				role="navigation">
				<div class="container">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse"
							data-target=".navbar-collapse">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="#">Twitt Tube</a>
					</div>
					<div class="navbar-collapse collapse">
						<ul class="nav navbar-nav">
							<li><a href="#about">About</a></li>
						</ul>
					</div>


				</div>
			</div>

		</div>
	</div>



	<!-- Carousel
    ================================================== -->
	<div id="myCarousel" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1"></li>
			<li data-target="#myCarousel" data-slide-to="2"></li>
		</ol>
		<div class="carousel-inner">
			<div class="item active">
				<!-- 
				<img
					data-src="holder/holder.js/900x500/auto/#777:#7a7a7a/text:First slide"
					alt="First slide"> -->
				<div class="container">
					<div class="carousel-caption">
						<!-- write something here -->
						<h1 id="test">Welcome</h1>
						<p>Welcome to Twitt-tube world. Start your lovely journey
							here!</p>

					</div>
				</div>
			</div>
			<div class="item">
				<!-- 
				<img
					data-src="holder/holder.js/900x500/auto/#666:#6a6a6a/text:Second slide"
					alt="Second slide"> -->
				<div class="container">
					<div class="carousel-caption"></div>
				</div>
			</div>
			<div class="item">
				<!-- 
				<img
					data-src="holder/holder.js/900x500/auto/#555:#5a5a5a/text:Third slide"
					alt="Third slide"> -->
				<div class="container">
					<div class="carousel-caption"></div>
				</div>
			</div>
		</div>
		<a class="left carousel-control" href="#myCarousel" data-slide="prev"><span
			class="glyphicon glyphicon-chevron-left"></span></a> <a
			class="right carousel-control" href="#myCarousel" data-slide="next"><span
			class="glyphicon glyphicon-chevron-right"></span></a>
	</div>
	<!-- /.carousel -->


	<div class="jumbotron">
		<div class="container">
			<form action="upload" method="post" enctype="multipart/form-data">
				<div class="upload" id="dc">
					<input type="file" capture="camera" id="cameraInput" name="upload"
						disabled />
				</div>
				<input type="hidden" id="inputUserId" name="userId" value="" /> <input
					type="submit" value="Upload" />
			</form>

		</div>
		<div class="container">
			<div id="fb-root"></div>
			<script>
				window.fbAsyncInit = function() {
					FB.init({
						appId : 382097358596865,
						status : true, // check login status
						cookie : true, // enable cookies to allow the server to access the session
						xfbml : true
					// parse XFBML
					});

					// Here we subscribe to the auth.authResponseChange JavaScript event. This event is fired
					// for any authentication related change, such as login, logout or session refresh. This means that
					// whenever someone who was previously logged out tries to log in again, the correct case below 
					// will be handled. 
					FB.Event
							.subscribe(
									'auth.authResponseChange',
									function(response) {
										// Here we specify what we do with the response anytime this event occurs. 
										if (response.status === 'connected') {
											document
													.getElementById("cameraInput").disabled = false;
											document.getElementById("dc").disabled = false;
											document.getElementById("dc").style.background = "url(new_conversation_btn.png)";
											document.getElementById("dc").style.backgroundSize = "200px 50px";
											FB
													.api(
															'/me',
															function(response1) {
																document
																		.getElementById('inputUserId').value = response1.id;
															});
										} else if (response.status === 'not_authorized') {
											// In this case, the person is logged into Facebook, but not into the app, so we call
											// FB.login() to prompt them to do so. 
											// In real-life usage, you wouldn't want to immediately prompt someone to login 
											// like this, for two reasons:
											// (1) JavaScript created popup windows are blocked by most browsers unless they 
											// result from direct interaction from people using the app (such as a mouse click)
											// (2) it is a bad experience to be continually prompted to login upon page load.
											document
													.getElementById("cameraInput").disabled = true;
											document.getElementById("dc").disabled = true;
											document.getElementById("dc").style.background = "url(new_conversation_btn_false.png)";
											document.getElementById("dc").style.backgroundSize = "200px 50px";

										} else {
											// In this case, the person is not logged into Facebook, so we call the login() 
											// function to prompt them to do so. Note that at this stage there is no indication
											// of whether they are logged into the app. If they aren't then they'll see the Login
											// dialog right after they log in to Facebook. 
											// The same caveats as above apply to the FB.login() call here.
											document
													.getElementById("cameraInput").disabled = true;
											document.getElementById("dc").disabled = true;
											document.getElementById("dc").style.background = "url(new_conversation_btn_false.png)";
											document.getElementById("dc").style.backgroundSize = "200px 50px";
										}
									});
				};

				// Load the SDK asynchronously
				(function(d) {
					var js, id = 'facebook-jssdk', ref = d
							.getElementsByTagName('script')[0];
					if (d.getElementById(id)) {
						return;
					}
					js = d.createElement('script');
					js.id = id;
					js.async = true;
					js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=382097358596865";
					ref.parentNode.insertBefore(js, ref);
				}(document));
			</script>
			<div class="fb-login-button" data-max-rows="2" data-size="xlarge"
				data-show-faces="true" data-auto-logout-link="true"></div>
		</div>
	</div>

	<!-- Marketing messaging and featurettes
    ================================================== -->
	<!-- Wrap the rest of the page in another container to center all the content. -->





	<%
		ArrayList<Conversation> conList = (ArrayList<Conversation>) request
			.getAttribute("conversationList");
	%>
	<%
		for (int i = 0; i < conList.size(); i++) {
		Conversation con = conList.get(i);
		ArrayList<Video> videoList = con.getVideoList();
	%>
	<div class="container marketing">


		<!-- START THE FEATURETTES -->


		<div id="myCarousel<%=con.getConversationId()%>"
			class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel<%=con.getConversationId()%>"
					data-slide-to="0" class="active"></li>
				<%
					for (int j = 1; j < videoList.size(); j++) {
				%>
				<li data-target="#myCarousel<%=con.getConversationId()%>"
					data-slide-to="<%=j%>"></li>
				<%
					}
				%>
			</ol>

			<div class="carousel-inner">
				<div class="item active">
					<!-- 
					<img data-src="holder/holder.js/900x500/auto/#777:#7a7a7a"> -->
					<div class="container">
						<div class="carousel-caption">
							<div class="col-md-7">
								<h2 class="featurette-heading">
									<%=con.getConversationId()%>
								</h2>
								<p class="lead">
									This is My
									<%=con.getConversationId()%>
									Conversation.Please join my conversation topic
								</p>
							</div>
							<div class="col-md-5">

								<%
									String url0 = videoList.get(0).getUrl();
								%>
								<div id="myElement<%=con.getConversationId()%>.0">Loading the player...</div>

								<script type="text/javascript">
              jwplayer("myElement<%=con.getConversationId()%>.0").setup({
                file: "<%=url0%>"

									});
								</script>

							</div>
						</div>
					</div>
				</div>
				<%
					for (int j = 1; j < videoList.size(); j++) {
						String videoUrl = videoList.get(j).getUrl();
				%>
				<div class="item">
					<!-- 
					<img data-src="holder/holder.js/900x500/auto/#777:#7a7a7a"> -->
					<div class="container">
						<div class="carousel-caption">
							<div class="col-md-7">
								<h2 class="featurette-heading">
									<%=con.getConversationId()%>
								</h2>
								<p class="lead">
									This is My
									<%=con.getConversationId()%>
									Conversation.Please join my conversation topic
								</p>
							</div>
							<div class="col-md-5">


								<div id="myElement<%=con.getConversationId()%>.<%=j%>">Loading the player...</div>

								<script type="text/javascript">
              jwplayer("myElement<%=con.getConversationId()%>.<%=j%>").setup({
                file: "<%=videoUrl%>"

									});
								</script>
							</div>
						</div>
					</div>
				</div>
				<%
					}
				%>
			</div>
			<a class="left carousel-control"
				href="#myCarousel<%=con.getConversationId()%>" data-slide="prev"><span
				class="glyphicon glyphicon-chevron-left"></span></a> <a
				class="right carousel-control"
				href="#myCarousel<%=con.getConversationId()%>" data-slide="next"><span
				class="glyphicon glyphicon-chevron-right"></span></a>

		</div>
		<hr class="featurette-divider">
	</div>
	<%
		}
	%>
	<!-- FOOTER -->
	<footer>
		<p class="pull-right">
			<a href="#">Back to top</a>
		</p>
		<p>
			&copy; 2014 Company, Inc. &middot; <a href="#">Privacy</a> &middot; <a
				href="#">Terms</a>
		</p>
	</footer>

	<!-- /.container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<!-- <script src="../../assets/js/docs.min.js"></script> -->
</body>
</html>
