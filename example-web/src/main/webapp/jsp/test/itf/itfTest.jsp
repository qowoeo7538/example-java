<%--
  Created by IntelliJ IDEA.
  User: joy
  Date: 17-5-21
  Time: 下午2:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../comm/comm.jspf" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="../../../js/Jquery/jquery-3.2.0.min.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<form action="/interaction/test" method="post" enctype="application/x-www-form-urlencoded">
    内容：<input name="logistics_interface" type="text" value="%3Corder%3E%3C%2Forder%3E"/>
    <br>
    验证码：<input name="data_digest" type="text" value="LghTkEmsD2tbQ3fsIBRcBg%3D%3D"/>
    <br>
    <input type="submit" value="提交">
</form>
</body>
</html>
