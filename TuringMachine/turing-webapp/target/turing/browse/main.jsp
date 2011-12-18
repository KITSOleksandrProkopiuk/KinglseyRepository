<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Turing</title>
<%@ include file="/browse/include.jspf"%>
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
<img src="image/busy-beaver-turing-machine.jpg"></img>
<div class="main_div_background">
<div class="table_div_main">
<div><c:if test="${not empty result_tape}">
	<c:out value="Result : "></c:out>

	<c:forEach var="item" items="${result_tape}">
		<c:out value="${item}"></c:out>
	</c:forEach>
	<br>
Steps : <c:out value="${counter}"></c:out>
</c:if></div>
<div style="color: red;">
<c:if test="${error != null}">
<c:out value="${error}"></c:out></c:if>
</div>

<form NAME="myform" action="process.do" method="post"><input
	type="text" name="tape" width="100%">

<table id="state_table" border="1px" width="100%">
	<tr>
		<th>State name</th>
		<th>Character to read</th>
		<th>Character to write</th>
		<th>Direction</th>
		<th>Next State</th>
	</tr>

</table>
<input type="submit" value="Process"></form>
<input id=button_add_row type="button" value="Add row" align="left">

</div>
<div id="add_row_dialog">
<h3 align="center">Fill the form</h3>
<table width="250px">
	<tr>
		<td width="130px" style="width: 77px;">State name</td>
		<td><input id="state_name" type="text" name="state_name"
			width="80px" style="width: 77px;"></td>
	</tr>
	<tr>
		<td width="130px" style="width: 77px;">Character to read</td>
		<td></td>
		<td><input id="to_read" type="text" name="to_read" width="80px"
			style="width: 77px;"></td>
	</tr>
	<tr>
		<td width="130px" style="width: 77px;">Character to write</td>
		<td></td>
		<td><input id="to_write" type="text" name="to_write" width="80px"
			style="width: 77px;"></td>
	</tr>
	<tr>
		<td width="130px" style="width: 77px;">Direction</td>
		<td><select id="direction" name="direction">
			<option>Right</option>
			<option>Left</option>
			<option>Stay</option>
		</select></td>
	</tr>

	<tr>
		<td width="130px" style="width: 77px;">Next State</td>
		<td><input id="next_state" type="text" name="next_state"
			width="80px" style="width: 77px;"></td>
	</tr>
	Nothing
</table>
</div>
</div>

</body>
</html>