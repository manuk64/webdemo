<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/standard" prefix="c" %><%@ taglib uri="/webtld" prefix="wd" %><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Ricerche Anagrafiche</title>
	<link type="text/css" rel="stylesheet" href="css/query.css">
	<link type="image/x-icon" href="img/logocomune.ico" rel="shortcut icon">
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>
<body>
<%@ include file="inc/head.jsp" %>
<div class="schedalogin"> Servizio di autenticazione<br /> <br />
<form action="login.jsp" method="post">
	<div class="rigalogin">
		<label for="login">Utente</label><input type="text" name="login" size="30" id="login"/>
	</div>
	<div class="rigalogin">
		<label for="password">Password</label><input type="password" name="password" size="30" id="password"/>
	</div>
	<div class="rigalogin">
		<input type="submit" value="Autorizza">
	</div>
	<div class="rigalogin">
		<a title="" href="http://centrale.comune.cagliari.it/">** Torna alla Intranet **</a>
	</div>

<wd:autenticazione nomelogin="veriflogin" nomemessaggio="msglogin" />

<c:if test="${null != veriflogin}" >
	<c:out value="${msglogin}" />
	<c:if test="${veriflogin.auth}" >
		<jsp:forward page="/index.jsp" />	
	</c:if>
</c:if>

</form>
</div>
<div class="piede">
<%@ include file="inc/foot.jsp" %>
</div>
</body>
</html>
