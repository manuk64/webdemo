<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/standard" prefix="c" %><%@ taglib uri="/webtld" prefix="wd" %><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Scheda di Famiglia Anagrafica</title>
	<link type="text/css" rel="stylesheet" href="css/query.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="image/x-icon" href="img/logocomune.ico" rel="shortcut icon">
</head>
<body>
<%@ include file="inc/head.jsp" %>

<c:if test="${null == veriflogin}" >
		<jsp:forward page="/login.jsp" />	
</c:if>
<c:if test="${null != veriflogin}" >
	<c:if test="${!veriflogin.auth}" >
		<jsp:forward page="/login.jsp" />	
	</c:if>
</c:if>

<div class="intestazione">Scheda di famiglia anagrafica</div>
<div class="scheda">Dati individuali
<wd:schedaFamiglia nomescheda="scheda" nomemessaggio="messaggio" />
<c:if test="${null != scheda}">
	<c:if test="${messaggio != ''}" ><div class="attenzione"><c:out value="${messaggio}" /></div></c:if>
<c:set value="0" var="swindirizzo" />
<c:forEach items="${scheda.famiglia}" var="content" >
	<c:set value="a" var="finale" /><c:if test="${content.sesso == 'M'}"><c:set value="o" var="finale" /></c:if>
	<div class="riga">
		<div class="elemento"><a href="individuale.jsp?codice=<c:out value="${content.codiceMaster}" />"><c:out value="${content.nome}" /></a></div>
		<div class="destro"><c:out value="${content.relazioneParentela}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Nat<c:out value="${finale}" /> a/il</div>
		<div class="destro"><c:out value="${content.comuneNascita}" /> / <c:out value="${content.dataNascita}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Paternit&agrave;</div>
		<div class="destro"><a href="individuale.jsp?codice=<c:out value="${content.codicePadre}" />"><c:out value="${content.padre}" /></a></div>
	</div>
	<div class="riga">
		<div class="elemento">Maternit&agrave;</div>
		<div class="destro"><a href="individuale.jsp?codice=<c:out value="${content.codiceMadre}" />"><c:out value="${content.madre}" /></a></div>
	</div>
	<div class="riga">
		<div class="elemento">Ultima residenza in</div>
		<div class="destro"><c:out value="${content.indirizzo.descrizioneVia}" /> <c:out value="${content.indirizzo.numeroCivico}" /> <c:out value="${content.indirizzo.bis}" /></div>
	</div>
	<div class="riga<c:if test="${content.stato == 'IRREPERIBILE' || content.stato == 'RESIDENTE' || content.stato == 'DECEDUTO' || content.stato == 'AIRE' || content.stato == 'EMIGRATO' }" ><c:out value="${content.stato}" /></c:if>">
		<div class="elemento">Stato</div>
		<div class="destro"><c:out value="${content.stato}" /></div>
	</div>
	<div class="riga">
		<div class="elemento"> </div>
		<div class="destro"> </div>
	</div>
</c:forEach>
</c:if>
		
</div>
<div class="piede">
<%@ include file="inc/foot.jsp" %>
</div>
</body>
</html>
 