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

<c:if test="${null == veriflogin}" >
		<jsp:forward page="/login.jsp" />	
</c:if>
<c:if test="${null != veriflogin}" >
	<c:if test="${!veriflogin.auth}" >
		<jsp:forward page="/login.jsp" />	
	</c:if>
</c:if>

<div class="intestazione">Ricerca anagrafica</div>
<div class="scheda"> Parametri di ricerca
<form action="index.jsp" method="post">
	<div class="riga">
		<div class="elementobb">Cognome e nome * </div>
		<input type="text" name="nomef"/>
	</div>
	<div class="riga">
		<div class="elemento">Cognome e nome padre </div>
		<input type="text" name="nomep"/>
	</div>
	<div class="riga">
		<div class="elemento">Cognome e nome madre </div>
		<input type="text" name="nomem"/>
	</div>
	<div class="riga">
		<div class="elemento">Sesso</div>
		<select name="sesso"><option value="%">Tutti</option><option value="M">Maschile</option><option value="F">Femminile</option></select>
	</div>
	<div class="riga">
		<div class="elemento">Nato nel comune di </div>
		<input type="text" name="luonas" />
	</div>
	<div class="riga">
		<div class="elementobb">Nato il (gg/mm/aaaa) * </div>
		<input type="text" name="datnas" />
	</div>
	<div class="riga">
		<div class="elemento">Nato tra l'anno </div>
		<input type="text" name="annoda" />
	</div>
	<div class="riga">
		<div class="elemento">e l'anno </div>
		<input type="text" name="annoa"  />
	</div>
	<div class="riga">
		<div class="elementobb">Codice fiscale * </div>
		<input type="text" name="codfis" />
	</div>
	<div class="riga">
		<div class="elemento"><input type="submit" value="Cerca Residente"></div>
	</div>
</form>
</div>

<wd:residenti nomelista="listaResidenti" nomemessaggio="messageRes" />
<wd:indirizzi nomelista="listaIndirizzi" nomemessaggio="messageInd" />

<div class="residenze">Risultati
<c:if test="${null != listaResidenti}" >
	<c:if test="${messageRes != ''}" ><div class="attenzione"><c:out value="${messageRes}" /></div></c:if>
<c:set value="0" var="swresid" /> 
<c:set value="0" var="swnoresid" />
<c:forEach items="${listaResidenti.listaResidenti}" var="content" >
	<c:if test="${content.stato == 'RESIDENTE'}" >
		<c:if test="${swresid == 0}" >
			<c:set value="1" var="swresid" />
			<div class="rigaresidenza">Residenti</div>
		</c:if>
	</c:if>
	<c:if test="${content.stato != 'RESIDENTE'}" >
		<c:if test="${swnoresid == 0}">
			<c:set value="1" var="swnoresid" />
			<div class="rigaresidenza" >Non Residenti</div>
		</c:if>
	</c:if>
	<div class="rigaresidenza"><div class="nome"><a href="individuale.jsp?codice=<c:out value="${content.codiceMaster}" />"><c:out value="${content.nome}"></c:out> </a></div><div class="datanascita"><c:out value="${content.dataNascita}"/></div></div>
</c:forEach>
</c:if>

<c:if test="${null != listaIndirizzi}" >
	<c:if test="${messageInd != ''}" ><div class="attenzione"><c:out value="${messageInd}" /></div></c:if>
<c:forEach items="${listaIndirizzi.indirizzi}" var="content" >
	<div class="rigaresidenza"><div class="nome"><c:out value="${content.descrizioneVia}" /></div>
	<div class="datanascita"><a href="index.jsp?codvia=<c:out value="${content.codiceVia}" />&amp;codcom=<c:out value="${content.codiceComune}" />&amp;civ=<c:out value="${content.numeroCivico}" />&amp;bis=<c:out value="${content.bis}" />"><c:out value="${content.numeroCivico}"/> <c:out value="${content.bis}"/></a></div></div>
</c:forEach>
</c:if>

</div>

<div class="scheda"> Cerca per indirizzo
<form action="index.jsp" method="post">
	<div class="riga">
		<div class="elemento">Indirizzo</div>
		<input type="text" name="indirizzo" />
	</div>
	<div class="riga">
		<div class="elemento">Numero civico</div>
		<input type="text" name="civico" />
	</div>
	<div class="riga">
		<div class="elemento"><input type="submit" value="Cerca Indirizzo"></div>
	</div>
</form>
</div>
<div class="scheda"> Istruzioni per la ricerca:
	<div class="riga">
	Almeno uno degli elementi con l'asterisco nella ricerca per nominativo deve essere valorizzato, 
	mentre nella ricerca per indirizzo i due campi sono obbligatori. Quindi ad esempio: 
	</div>
	<div class="riga">
	- solo il nominativo;
	</div>
	<div class="riga">
	- solo la data di nascita;
	</div>
	<div class="riga">
	- solo il codice fiscale;
	</div>
	<div class="riga">
	- il nominativo e una data di nascita;
	</div>
	<div class="riga">
	- il nominativo e un intervallo tra anno e anno di nascita;
	</div>
	<div class="riga">
	- cognome proprio, cognome padre e/o cognome madre;
	</div>
	<div class="riga">
	 oppure
	</div>
	<div class="riga">
	- per indirizzo, indicando il nome della via con il civico
	</div>
	<div class="riga">
	<p>&nbsp;</p>
	</div>
</div>
<div class="piede">
<%@ include file="inc/foot.jsp" %>
<c:if test="${null != veriflogin}" >
 - <a href="logout.jsp" title="Termina la ricerca">Esci</a>
</c:if>
</div>
</body>
</html>
