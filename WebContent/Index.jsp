<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="model.Conversation"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
	
</script>
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


<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../assets/ico/favicon.ico">

<title>Twitt-Tube</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
	<%
		if (request.getAttribute("UploadStatus") != null) {
			String status = (String) request.getAttribute("UploadStatus");
	%>
	<div id='message' style="display: none;">
		<span><%=status%></span>
	</div>
	<%
		}
	%>

	<div class="navbar navbar-inverse" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<!-- 				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button> -->
				<a class="navbar-brand" href="#">Twitt-Tube</a>
			</div>
			<div class="navbar-collapse collapse">
				<div class="navbar-form navbar-right">
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
							FB.Event.subscribe(
											'auth.authResponseChange',
											function(response) {
												// Here we specify what we do with the response anytime this event occurs. 
												if (response.status === 'connected') {
													document.getElementById("cameraInput").disabled = false;
													document.getElementById("dc").disabled = false;
													document.getElementById("dc").style.background = "url(new_conversation_btn.png)";
													document.getElementById("dc").style.backgroundSize = "200px 50px";
													FB.api('/me',function(response1) {
														document.getElementById('inputUserId').value = response1.id;
													});
												} else if (response.status === 'not_authorized') {
													// In this case, the person is logged into Facebook, but not into the app, so we call
													// FB.login() to prompt them to do so. 
													// In real-life usage, you wouldn't want to immediately prompt someone to login 
													// like this, for two reasons:
													// (1) JavaScript created popup windows are blocked by most browsers unless they 
													// result from direct interaction from people using the app (such as a mouse click)
													// (2) it is a bad experience to be continually prompted to login upon page load.
													document.getElementById("cameraInput").disabled = true;
													document.getElementById("dc").disabled = true;
													document.getElementById("dc").style.background = "url(new_conversation_btn_false.png)";
													document.getElementById("dc").style.backgroundSize = "200px 50px";

												} else {
													// In this case, the person is not logged into Facebook, so we call the login() 
													// function to prompt them to do so. Note that at this stage there is no indication
													// of whether they are logged into the app. If they aren't then they'll see the Login
													// dialog right after they log in to Facebook. 
													// The same caveats as above apply to the FB.login() call here.
													document.getElementById("cameraInput").disabled = true;
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
							js.src = "//connect.facebook.net/en_US/all.js";
							ref.parentNode.insertBefore(js, ref);
						}(document));
					</script>
					<div class="fb-login-button" data-max-rows="2" data-size="medium"
						data-show-faces="true" data-auto-logout-link="true"></div>
				</div>

			</div>
			<!--/.navbar-collapse -->
		</div>
	</div>


	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<div class="container">
			<h1 id="test">Welcome</h1>
			<p>Welcome to Twitt-tube world. Start your lovely journey here!</p>
			<form action="upload" method="post" enctype="multipart/form-data">
				<div class="upload" id="dc">
					<input type="file" capture="camera" accept="video/*"
						id="cameraInput" name="upload" disabled/>
				</div>
				<input type="hidden" id="inputUserId" name="userId" value="" /> <input
					type="submit" value="Upload" />
			</form>

		</div>
	</div>


	<div class="container">
		<%
			ArrayList<Conversation> conList = (ArrayList<Conversation>) request
					.getAttribute("conversationList");

			for (int i = 0; i < conList.size(); i++) {
		%>
		<div class="row">
			<%
				for (int j = 0; j < 3; j++, i++) {
						if (i < conList.size()) {
			%>
			<div class="col-md-4">
				<h2><%=conList.get(i).getConversationId()%></h2>
				<video id="example_video_1"
					class="video-js vjs-default-skin vjs-big-play-centered" controls
					preload="auto" width="320" height="240"
					poster="http://video-js.zencoder.com/oceans-clip.png"
					data-setup='{"example_option":true}'> <%
 	String url = conList.get(i).getVideoList().get(0)
 						.getUrl();
 %> <source src="<%=url%>" type="video/mp4"></video>
			</div>
			<%
				}
					}
			%>
		</div>
		<%
			}
		%>

		<hr>

		<footer>
		<p>&copy; Company 2014</p>
		</footer>
	</div>
	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
