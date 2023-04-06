<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/standard" prefix="c" %><%@ taglib uri="/webtld" prefix="wd" %><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Scheda Anagrafica</title>
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

<div class="intestazione">Scheda anagrafica</div>
<wd:schedaIndividuale nomescheda="scheda" />
<c:set value="${scheda}" var="content" />

<div class="avviso<c:if test="${content.stato == 'IRREPERIBILE' || content.stato == 'RESIDENTE' || content.stato == 'DECEDUTO' || content.stato == 'AIRE' || content.stato == 'EMIGRATO' }" ><c:out value="${content.stato}" /></c:if>"><strong><c:out value="${content.stato}" /></strong></div>
<div class="scheda">Dati individuali
	<div class="riga">
		<div class="elemento">Codice Interno</div>
		<div class="destro"><c:out value="${content.codiceMaster}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Codice Fiscale</div>
		<div class="destro"><c:out value="${content.codiceFiscale}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Nominativo</div>
		<div class="destro"><c:out value="${content.nome}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Sesso</div>
		<div class="destro"><c:out value="${content.sesso}" /></div>
	</div>
	<div class="riga">		
		<div class="elemento">Data di nascita</div>
		<div class="destro"><c:out value="${content.dataNascita}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Comune di nascita</div>
		<div class="destro"><c:out value="${content.comuneNascita}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Cittadinanza</div>
		<div class="destro"><c:out value="${content.nazionalita}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Paternità</div>
		<div class="destro"><c:if test="${content.codicePadre != '0'}"><a href="individuale.jsp?codice=<c:out value="${content.codicePadre}" />"><c:out value="${content.padre}" /></a></c:if></div>
	</div>
	<div class="riga">
		<div class="elemento">Maternità</div>
		<div class="destro"><c:if test="${content.codiceMadre != '0'}"><a href="individuale.jsp?codice=<c:out value="${content.codiceMadre}" />"><c:out value="${content.madre}" /></a></c:if></div>
	</div>
	<div class="riga">
		<div class="elemento">Residenza</div>
		<div class="destro"><c:out value="${content.comuneResidenza}" /></div>	
	</div>
	<div class="riga">
		<div class="elemento">Indirizzo</div>
		<div class="destro"><c:out value="${content.indirizzo.descrizioneVia}" /> n° <a href="index.jsp?codvia=<c:out value="${content.indirizzo.codiceVia}" />&amp;codcom=<c:out value="${content.indirizzo.codiceComune}" />&amp;civ=<c:out value="${content.indirizzo.numeroCivico}" />&amp;bis=<c:out value="${content.indirizzo.bis}" />"><c:out value="${content.indirizzo.numeroCivico}" /> <c:out value="${content.indirizzo.bis}" /></a></div>	
	</div>
	<div class="riga">
		<div class="elemento">Scala</div>
		<div class="destro"><c:out value="${content.indirizzo.scala}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Piano</div>
		<div class="destro"><c:out value="${content.indirizzo.piano}" /></div>		
	</div>
	<div class="riga">
		<div class="elemento">Interno</div>
		<div class="destro"><c:out value="${content.indirizzo.interno}" /></div>		
	</div>
	<c:if test="${content.stato == 'IRREPERIBILE'}">
	<div class="riga">
		<div class="elemento">Irreperibile dal</div>
		<div class="destro"><c:out value="${content.dataEmigrazione}" /></div>
	</div>
	<c:if test="${content.codiceComuneRicomparsa > 0}">
	<div class="riga">
		<div class="elemento">Ricomparso a/il</div>
		<div class="destro"><c:out value="${content.descrizioneComuneRicomparsa}" /> <c:out value="${content.dataRicomparsa}" /></div>
	</div>
	</c:if>
	</c:if>
	<div class="riga">
		<div class="elemento">Emigrato a/il</div>
		<div class="destro"><c:out value="${content.comuneEmigrazione}" /> <c:out value="${content.dataEmigrazione}" /></div>
	</div>
	<div class="riga">
	<c:if test="${content.iscritto == '004'}">
		<div class="elemento">Ricomparso dal</div>
		<div class="destro"><c:out value="${content.dataImmigrazione}" /></div>
	</c:if>
	<c:if test="${content.iscritto != '004'}">
		<div class="elemento">Immigrato da/il</div>
		<div class="destro"><c:out value="${content.comuneImmigrazione}" /> <c:out value="${content.dataImmigrazione}" /></div>
	</c:if>
	</div>
	<div class="riga">
		<div class="elemento">Coniugato con</div>
		<div class="destro"><c:if test="${content.codiceConiuge != '0'}"><a href="individuale.jsp?codice=<c:out value="${content.codiceConiuge}" />"><c:out value="${content.coniuge}" /></a></c:if></div>
	</div>
	<div class="riga">
		<div class="elemento">Sposato in/il	</div>
		<div class="destro"><c:out value="${content.comuneMatrimonio}" /> <c:out value="${content.dataMatrimonio}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Divorziato in/il	</div>
		<div class="destro"><c:out value="${content.tribunaleDivorzio}" /> <c:out value="${content.dataDivorzio}" /></div>
	</div>
	<div class="riga">	
		<div class="elemento">Deceduto a/il</div>
		<div class="destro"><c:out value="${content.comuneMorte}" /> <c:out value="${content.dataMorte}" /></div>
	</div>
	<div class="riga">	
		<div class="elemento">Carta d'identità</div>
		<div class="destro"><c:out value="${content.cartaIdentita}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Emessa il</div>
		<div class="destro"><c:out value="${content.emissioneCartaIdentita}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Scadenza il </div>
		<div class="destro"><c:out value="${content.scadenzaCartaIdentita}" /></div>
	</div>
</div>
<div class="scheda">Variazioni Residenza<c:forEach items="${content.variazioniResidenze}" var="variazioni">
	<div class="rigavariazioni"><c:out value="${variazioni}" /></div></c:forEach>
</div>
<div class="scheda">Richieste di variazione<c:forEach items="${content.variazioneAppesa}" var="richieste">
	<div class="rigavariazioni"><c:out value="${richieste}" /></div></c:forEach>
</div>
<div class="scheda">Dati Elettorali
	<div class="riga">
		<div class="elemento">N° Generale</div>
		<div class="destro"><c:out value="${content.numeroElettorale}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Numero Sezione</div>
		<div class="destro"><c:out value="${content.numeroSezione}" /></div>
	</div>
	<div class="riga">
	<div class="elemento">Luogo</div>
		<div class="destro"><c:out value="${content.sezioneElettorale}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Collegio/Camera</div>
		<div class="destro"><c:out value="${content.camera}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Collegio/Prov</div>
		<div class="destro"><c:out value="${content.provinciaElettorale}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Collegio/Europee</div>
		<div class="destro"><c:out value="${content.europee}" /></div>
	</div>
	<div class="riga">
		<div class="elemento">Circoscrizione</div>
		<div class="destro"><c:out value="${content.circoscrizione}" /></div>
	</div>
	<div class="riga">
		<div class="elemento"></div>
		<div class="destro"><a href="famiglia.jsp?famiglia=<c:out value="${content.codiceFamiglia}" />&amp;indi=<c:out value="${content.codiceMaster}" />">Scheda di Famiglia</a></div>
	</div>
</div>
<div class="piede">
<%@ include file="inc/foot.jsp" %>
</div>
</body>
</html>
