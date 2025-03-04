<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="titrePage"><i class="bi ${titrePage.icone} pe-2"></i>${titrePage.nomPage}</c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="container g-z-index-1 g-py-20" >
        <h3>${user.firstname}</h3>
        <h2>${user.lastname}</h2>
    </div>
</c:set>
<c:set var="jsSpec">
</c:set>
<%@ include file="templatePageAvecMenuV.jsp"%>