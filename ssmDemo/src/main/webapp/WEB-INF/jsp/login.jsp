<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Login</title>
<!-- Bootstrap -->
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="./bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="./css/log/bootstrap.min.css" rel="stylesheet">
<link href="./css/log/metisMenu.min.css" rel="stylesheet">
<link href="./css/log/sb-admin-2.css" rel="stylesheet">
<link href="./css/log/font-awesome.min.css" rel="stylesheet"
	type="text/css">


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="./js/html5shiv.min.js"></script>
      <script src="./js/respond.min.js"></script>
    <![endif]-->
<body  style="background-color:white">

<div align="center">
	<table width="200" border="0" cellpadding="0" cellspacing="0"
		height="7">
		<tr>
			<td><img border="0" height=100 width=300 src="./images/logo.jpg"></td>
		</tr>
<!-- 		<td width="179" valign="top" bgcolor="#ffffff" align="left" -->
<!-- 			height="95" rowspan="0"> -->
<!-- 			<table border="0" cellpadding="0" cellspacing="0" height="50%"> -->
<!-- 				<tr> -->
<!-- 					<td width="30%"></td> -->
<!-- 					<td><img align=Center height=250 width=380 -->
<!-- 						src="./images/phone.jpg" alt="Logon"></td> -->
<!-- 				</tr> -->
<!-- 		</table> -->
<!-- 		</td> -->
		
		<td valign="top" bgcolor="#ffffff" align="center" width="300">
			<div class="container"  >
					<div style="width:380px;">
						<div class="login-panel panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Please Sign In</h3>
							</div>
							<div class="panel-body">
								<form role="form" action="./login.do" method="post">
									<fieldset>
										<span style="color: red;">${message_login }</span>
										<div class="form-group">
											<span style="color: red;">${username_error }</span> <input
												class="form-control" id="inputEmail3" placeholder="username"
												name="username" type="username" autofocus>
										</div>
										<div class="form-group">
											<span style="color: red;">${password_error }</span> <input
												class="form-control" placeholder="Password" name="password"
												type="password">
										</div>
										<button type="submit" class="btn btn-lg btn-primary btn-block">Submit</button>
									</fieldset>
								</form>
							</div>
						</div>
					</div>
				</div>
				</td>
				</table>
	</div>			
					<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<p align="center">
		<font face="Times New Roman" size="1" color="blue">&copy;Copyright
			2016-2020, Lenovo Mobility, Inc. All rights reserved<br>Lenovo
			Mobility Internal Use Only
		</font>
	</p>
	</div>


	<script src="./js/jquery.min.js"></script>
	<script src="./bootstrap/js/bootstrap.min.js"></script>
	<script src="./js/log/jquery.min.js"></script>
	<script src="./js/log/bootstrap.min.js"></script>
	<script src="./js/log/metisMenu.min.js"></script>
	<script src="./js/log/sb-admin-2.js"></script>
</body>
</html>