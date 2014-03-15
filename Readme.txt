ASSIGNMENT1  - READ ME 

Our web app have 2 pages 

On the Home page, users can log in using their Facebook account(we used Facebook API to write the login function for our website), otherwise the user cannot reply to a conversation or create a new Conversation.

1. On the home page, we are shown the top videos of every conversation. By clicking on “detail”, we can go to the second page where there are all the videos in the page.

2. When we click on a conversation reply button, second page opens which will list the exchanged video messages over this conversation



Yunzhi Ye: yy2509
Siyang Wu: sw2848

******************
URL
******************

我們最後的URL（替換）
http://cloudtube2-xjnwssfbsd.elasticbeanstalk.com/

******************
EXTRA POINT
******************

Use of Elastic Transcoder 
Use of SNS 


******************
Database Schema (Storing the info about videos)
******************


Field		Type
videoName	varchar(32)		
conversationId	bigint(20)		
videoTimestamp	bigint(20)		
url		varchar(100)			
username	varchar(32)	
