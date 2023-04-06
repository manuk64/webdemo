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
<div class="schedalogin"> Disconnessione...<br /> <br />
<wd:autenticazione nomelogin="veriflogin" nomemessaggio="msglogin" />
<jsp:forward page="/index.jsp" />	
</div>
<div class="piede">
<%@ include file="inc/foot.jsp" %>
</div>
</body>
</html>
