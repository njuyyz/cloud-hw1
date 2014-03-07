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
	background: url(new_conversation_btn.png);
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
	<!-- Main jumbotron for a primary marketing message or call to action -->


	<script async src="https://connect.facebook.net/en_US/all.js" >
		window.fbAsyncInit = function() {
			FB.init({
				appId : 382097358596865,
				status : true,
				xfbml : true
			});
		};

		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id)) {
				return;
			}
			js = d.createElement(s);
			js.id = id;
			js.src = "//connect.facebook.net/en_US/all.js";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
	</script>
	<fb:login-button show-faces="true" width="200" max-rows="1"></fb:login-button>
	<div id="fb-root"></div>
	
	<div class="jumbotron">
		<div class="container">
			<h1>Welcome</h1>
			<p>Welcome to Twitt-tube world. Start your lovely journey here!</p>
			<form action="upload" method="post" enctype="multipart/form-data">
				<div class="upload">
					<input type="file" capture="camera" accept="video/*"
						id="cameraInput" name="upload" />
				</div>
				<input type="submit" value="Upload" />
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
