<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>LClaim</title>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath %>" />
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/styles.css" rel="stylesheet" media="screen">
<link href="./datatables/css/dataTables.bootstrap.min.css" rel="stylesheet" media="screen">
<!--[if lt IE 9]>
      <script src="./js/html5shiv.min.js"></script>
      <script src="./js/respond.min.js"></script>
<![endif]-->
</head>
<body>
<div style=" position:fixed; top:0;left:0">
<a data-target="#content" href="./User/showAllUser.do">page分页</a>
<a data-target="#content" href="./User/uploadUserList.do">文件上传</a>
</div>
 <div class="container">
  <div class="row text-center">
   <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
     <td width="75%" class="ForLogin">&nbsp;<br>
      <table width="90%" border="0" hspace="40" align="center" cellpadding="1" cellspacing="1" bgcolor="#ccffff"
       style="WIDTH: 398px; HEIGHT: 206px">
       <TR>
        <td align=left width="75%" valign="top" bgcolor="#ffffff"><br>
         <P>
          <IMG align=middle height=40 src="./images/info.jpg" alt="info.jpg" width=146>
         </P>
         <P class="ForLogin" style="font-size: 14px">
          Welcome!<BR>
          <BR> Sessions time out after 1 hour of inactivity<BR> Accounts are deactivated after 90 days of
          inactivity<BR>
          <BR> For issue, open a Monet ticket to &#39;Global Service Applications&#39;<BR>
          <!-- http://vhd.mot-mobility.com/ -->
          <BR>
          <BR> *Requires Internet Explorer 9 or higher
         </P></td>
       </TR>
      </table>
      <p>&nbsp;</p>
    <tr>
     <td>&nbsp;</td>
    </tr>
    <tr>
     <td>
     </td>
    </tr>
   </table>
  </div>
 </div>
 ${com.ssm.demo.util.UserUtil.getCurUser}
<div style=" position:fixed; top:0;right:0">
   ${com.ssm.demo.util.UserUtil.getCurUser()}
   <a href="./logout.do">Logout</a>
</div>
</body>

<script src="./js/jquery.min.js"></script>
<script src="./bootstrap/js/bootstrap.min.js"></script>
<script src="./js/bootstrap-hover-dropdown.js"></script>
</html>