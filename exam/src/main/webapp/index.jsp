<%@ page language="java" contentType="text/html; charset=windows-1251"
	pageEncoding="CP1251"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
<title>Test</title>
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>

<div class="div_general">
<div class="div_dialog_field">Привіт, для продовження введіть своє
ім'я:
<% request.setCharacterEncoding("CP1251"); %>
<form action="say.wellcome" method="POST" ><input type="text" name="user_name" >
<input type="submit" name="submit" value="Вхід"></form>
</div>
</div>
</body>